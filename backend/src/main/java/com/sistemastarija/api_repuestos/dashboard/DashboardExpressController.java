package com.sistemastarija.api_repuestos.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
// CAMBIO IMPORTANTE: En lugar de "*", ponemos la URL exacta de tu Angular.
// Si tu Angular corre en otro puerto que no sea 4200, cambia ese número.
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class DashboardExpressController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/flash-ganancias")
    public Map<String, Double> getFlashGanancias() {
        // Consulta nativa directa
        String sql = "SELECT SUM((d.precio_unitario_repuesto - d.costo_repuesto) * d.cantidad) " +
                "FROM detalle_venta d " +
                "JOIN venta v ON d.id_venta = v.id_venta " +
                "WHERE v.estado_venta = 1";

        // Ejecutar consulta
        // Nota: queryForObject puede lanzar excepción si devuelve null en versiones viejas,
        // pero con Double.class suele manejarlo bien. Agregamos try-catch por seguridad extrema.
        Double totalGanancia;
        try {
            totalGanancia = jdbcTemplate.queryForObject(sql, Double.class);
        } catch (Exception e) {
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
}