package com.sistemastarija.api_repuestos.dashboard;

/**
 * DTO para la vista ABC de inventario.
 * Representa un producto con sus métricas de valor y rotación.
 */
public class AbcItemDto {

    private int idRepuesto;
    private String nombreRepuesto;

    /** Cantidad total vendida (unidades). */
    private double cantidadVendida;

    /** Veces que apareció en ventas (número de movimientos). */
    private long vecesVendido;

    /** Precio sugerido actual del producto. */
    private double precioSugerido;

    /** Valor total = precioSugerido * cantidadVendida. */
    private double valorTotal;

    /** Clasificación ABC por valor (A/B/C). */
    private String clasificacionValor;

    /** Clasificación ABC por rotación (A/B/C). */
    private String clasificacionRotacion;

    public AbcItemDto() {
    }

    public int getIdRepuesto() {
        return idRepuesto;
    }

    public void setIdRepuesto(int idRepuesto) {
        this.idRepuesto = idRepuesto;
    }

    public String getNombreRepuesto() {
        return nombreRepuesto;
    }

    public void setNombreRepuesto(String nombreRepuesto) {
        this.nombreRepuesto = nombreRepuesto;
    }

    public double getCantidadVendida() {
        return cantidadVendida;
    }

    public void setCantidadVendida(double cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
    }

    public long getVecesVendido() {
        return vecesVendido;
    }

    public void setVecesVendido(long vecesVendido) {
        this.vecesVendido = vecesVendido;
    }

    public double getPrecioSugerido() {
        return precioSugerido;
    }

    public void setPrecioSugerido(double precioSugerido) {
        this.precioSugerido = precioSugerido;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getClasificacionValor() {
        return clasificacionValor;
    }

    public void setClasificacionValor(String clasificacionValor) {
        this.clasificacionValor = clasificacionValor;
    }

    public String getClasificacionRotacion() {
        return clasificacionRotacion;
    }

    public void setClasificacionRotacion(String clasificacionRotacion) {
        this.clasificacionRotacion = clasificacionRotacion;
    }
}
