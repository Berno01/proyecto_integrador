package com.sistemastarija.api_repuestos.dashboard;

/**
 * DTO para las alertas de Punto de Reorden (ROP).
 * Contiene los datos que usará el front para pintar la tabla
 * y mostrar si un producto está en zona de "Hacer Pedido".
 */
public class RopAlertaDto {

    private int idRepuesto;
    private String nombreRepuesto;

    private int stockActual;
    private int tiempoEntrega;           // días de lead time
    private int stockSeguridad;

    private double demandaAnual;        // D
    private double demandaDiaria;       // D/365 calculado
    private double puntoReorden;        // ROP = d * L + SS

    private boolean enAlerta;           // true si stockActual <= ROP

    public RopAlertaDto() {
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

    public int getStockActual() {
        return stockActual;
    }

    public void setStockActual(int stockActual) {
        this.stockActual = stockActual;
    }

    public int getTiempoEntrega() {
        return tiempoEntrega;
    }

    public void setTiempoEntrega(int tiempoEntrega) {
        this.tiempoEntrega = tiempoEntrega;
    }

    public int getStockSeguridad() {
        return stockSeguridad;
    }

    public void setStockSeguridad(int stockSeguridad) {
        this.stockSeguridad = stockSeguridad;
    }

    public double getDemandaAnual() {
        return demandaAnual;
    }

    public void setDemandaAnual(double demandaAnual) {
        this.demandaAnual = demandaAnual;
    }

    public double getDemandaDiaria() {
        return demandaDiaria;
    }

    public void setDemandaDiaria(double demandaDiaria) {
        this.demandaDiaria = demandaDiaria;
    }

    public double getPuntoReorden() {
        return puntoReorden;
    }

    public void setPuntoReorden(double puntoReorden) {
        this.puntoReorden = puntoReorden;
    }

    public boolean isEnAlerta() {
        return enAlerta;
    }

    public void setEnAlerta(boolean enAlerta) {
        this.enAlerta = enAlerta;
    }
}
