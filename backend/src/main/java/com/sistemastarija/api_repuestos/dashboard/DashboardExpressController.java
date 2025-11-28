package com.sistemastarija.api_repuestos.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Controlador "rápido" para el panel de control.
 * Aquí centralizamos:
 *  - Flash financiero (ganancias)
 *  - Clasificación ABC
 *  - Alertas de Punto de Reorden (ROP)
 */
@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class DashboardExpressController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // ==========================================================
    // 1. FLASH FINANCIERO (ya lo tenías)
    // ==========================================================
    @GetMapping("/flash-ganancias")
    public Map<String, Double> getFlashGanancias() {

        String sql = "SELECT SUM((d.precio_unitario_repuesto - d.costo_repuesto) * d.cantidad) " +
                "FROM detalle_venta d " +
                "JOIN venta v ON d.id_venta = v.id_venta " +
                "WHERE v.estado_venta = 1";

        Double totalGanancia;
        try {
            totalGanancia = jdbcTemplate.queryForObject(sql, Double.class);
        } catch (Exception e) {
            e.printStackTrace();
            totalGanancia = 0.0;
        }

        if (totalGanancia == null) {
            totalGanancia = 0.0;
        }

        Double gananciaSocia = totalGanancia / 2;

        Map<String, Double> response = new HashMap<>();
        response.put("total_ganancia", totalGanancia);
        response.put("ganancia_socia", gananciaSocia);

        return response;
    }

    // ==========================================================
    // 2. CLASIFICACIÓN ABC (valor + rotación)
    // Endpoint: GET /api/dashboard/abc1
    // ==========================================================
    @GetMapping("/abc1")
    public List<Map<String, Object>> getAbcClasificacion() {

        // Traemos por cada repuesto:
        // - Cantidad vendida
        // - Veces vendido
        // - Precio sugerido
        // - Valor total (cantidad * precio)
        String sql = """
                SELECT
                    r.id_repuesto                     AS idRepuesto,
                    r.nombre_repuesto                 AS nombreProducto,
                    COALESCE(SUM(dv.cantidad), 0)     AS cantidadVendida,
                    COUNT(dv.id_detalle_venta)        AS vecesVendido,
                    r.precio_sugerido                 AS precioSugerido,
                    COALESCE(SUM(dv.cantidad) * r.precio_sugerido, 0) AS valorTotal
                FROM repuesto r
                LEFT JOIN detalle_venta dv ON dv.id_repuesto = r.id_repuesto
                GROUP BY r.id_repuesto, r.nombre_repuesto, r.precio_sugerido
                """;

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        if (rows.isEmpty()) {
            return Collections.emptyList();
        }

        // -----------------------------
        // ABC por VALOR (A=80%, B=15%, C=5%)
        // -----------------------------
        List<Map<String, Object>> ordenValor = new ArrayList<>(rows);
        ordenValor.sort((a, b) -> {
            double v1 = ((Number) b.get("valorTotal")).doubleValue();
            double v0 = ((Number) a.get("valorTotal")).doubleValue();
            return Double.compare(v1, v0);
        });

        double totalValor = ordenValor.stream()
                .mapToDouble(r -> ((Number) r.get("valorTotal")).doubleValue())
                .sum();

        Map<Object, String> abcPorValor = new HashMap<>();
        double acumulado = 0.0;

        for (Map<String, Object> r : ordenValor) {
            Object id = r.get("idRepuesto");
            double valor = ((Number) r.get("valorTotal")).doubleValue();
            acumulado += valor;

            double porcentaje = (totalValor <= 0) ? 0 : (acumulado / totalValor);

            String categoria;
            if (porcentaje <= 0.80) {
                categoria = "A";
            } else if (porcentaje <= 0.95) {
                categoria = "B";
            } else {
                categoria = "C";
            }
            abcPorValor.put(id, categoria);
        }

        // -----------------------------
        // ABC por ROTACIÓN (top 20% A, sig. 30% B, resto C)
        // -----------------------------
        List<Map<String, Object>> ordenRotacion = new ArrayList<>(rows);
        ordenRotacion.sort((a, b) -> {
            int v1 = ((Number) b.get("vecesVendido")).intValue();
            int v0 = ((Number) a.get("vecesVendido")).intValue();
            return Integer.compare(v1, v0);
        });

        Map<Object, String> abcPorRotacion = new HashMap<>();
        int n = ordenRotacion.size();

        for (int i = 0; i < n; i++) {
            Map<String, Object> r = ordenRotacion.get(i);
            Object id = r.get("idRepuesto");
            double posicion = (i + 1) / (double) n;

            String categoria;
            if (posicion <= 0.20) {
                categoria = "A";
            } else if (posicion <= 0.50) {
                categoria = "B";
            } else {
                categoria = "C";
            }

            abcPorRotacion.put(id, categoria);
        }

        // -----------------------------
        // Construimos la respuesta final
        // -----------------------------
        List<Map<String, Object>> resultado = new ArrayList<>();

        for (Map<String, Object> r : ordenValor) {
            Object id = r.get("idRepuesto");

            Map<String, Object> fila = new LinkedHashMap<>();
            fila.put("idRepuesto", id);
            fila.put("nombreProducto", r.get("nombreProducto"));
            fila.put("cantidadVendida", r.get("cantidadVendida"));
            fila.put("vecesVendido", r.get("vecesVendido"));
            fila.put("precioSugerido", r.get("precioSugerido"));
            fila.put("valorTotal", r.get("valorTotal"));

            fila.put("abcValor", abcPorValor.getOrDefault(id, "-"));
            fila.put("abcRotacion", abcPorRotacion.getOrDefault(id, "-"));

            resultado.add(fila);
        }

        return resultado;
    }

    // ==========================================================
    // 3. ALERTAS DE PUNTO DE REORDEN (ROP)
    // Endpoint: GET /api/dashboard/rop-alertas
    // ==========================================================
    @GetMapping("/rop-alertas")
public List<Map<String, Object>> getRopAlertas() {

    // 1) Traemos los campos necesarios de la tabla repuesto
    String sql = """
            SELECT
                id_repuesto,
                nombre_repuesto,
                stock_actual,
                tiempo_entrega,
                stock_seguridad,
                demanda_anual_estimada
            FROM repuesto
            WHERE estado_repuesto = 1
            """;

    try {
        List<Map<String, Object>> alertas = new ArrayList<>();

        jdbcTemplate.query(sql, rs -> {
            int idRepuesto      = rs.getInt("id_repuesto");
            String nombre       = rs.getString("nombre_repuesto");
            int stockActual     = rs.getInt("stock_actual");
            int leadTimeDias    = rs.getInt("tiempo_entrega");
            int stockSeguridad  = rs.getInt("stock_seguridad");

            // OJO: aquí usamos el nombre correcto de la columna
            double demandaAnual = rs.getDouble("demanda_anual_estimada");

            // 2) Cálculo de demanda diaria (simplificado)
            double demandaDiaria = demandaAnual / 365.0;

            // 3) Fórmula de ROP: ROP = d * L + SS
            double rop = demandaDiaria * leadTimeDias + stockSeguridad;

            // 4) Si el stock actual está por debajo o igual al ROP ⇒ generar alerta
            if (stockActual <= rop) {
                Map<String, Object> fila = new HashMap<>();
                fila.put("id_repuesto", idRepuesto);
                fila.put("producto", nombre);
                fila.put("stock_actual", stockActual);
                fila.put("tiempo_entrega", leadTimeDias);
                fila.put("stock_seguridad", stockSeguridad);
                fila.put("demanda_diaria", demandaDiaria);
                fila.put("rop_calculado", rop);
                fila.put("diferencia", stockActual - rop); // negativo = crítico
                alertas.add(fila);
            }
        });

        return alertas;

    } catch (Exception e) {
        System.err.println("Error en getRopAlertas: " + e.getMessage());
        e.printStackTrace();
        // Para que el front no muera, devolvemos lista vacía en caso de error
        return Collections.emptyList();
    }
}

}
