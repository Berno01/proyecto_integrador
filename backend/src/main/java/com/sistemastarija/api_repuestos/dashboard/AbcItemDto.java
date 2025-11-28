package com.sistemastarija.api_repuestos.dashboard;

public class AbcItemDto {

    private int idRepuesto;
    private String nombreRepuesto;
    private long cantidadVendida;
    private long vecesVendido;
    private double precioSugerido;
    private double valorTotal;
    private String clasificacionPorValor;
    private String clasificacionPorRotacion;

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

    public long getCantidadVendida() {
        return cantidadVendida;
    }

    public void setCantidadVendida(long cantidadVendida) {
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

    public String getClasificacionPorValor() {
        return clasificacionPorValor;
    }

    public void setClasificacionPorValor(String clasificacionPorValor) {
        this.clasificacionPorValor = clasificacionPorValor;
    }

    public String getClasificacionPorRotacion() {
        return clasificacionPorRotacion;
    }

    public void setClasificacionPorRotacion(String clasificacionPorRotacion) {
        this.clasificacionPorRotacion = clasificacionPorRotacion;
    }
}
