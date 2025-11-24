package com.sistemastarija.api_repuestos.venta.domain.model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
public class Venta
{
    private Integer idVenta;
    private String nombreCliente;
    private LocalDateTime fechaVenta;
    private Double total;
    private Double descuentoTotal;
    private List<DetalleVenta> detalleVenta;
    private Boolean estadoVenta;

    public Venta(Integer idVenta, String nombreCliente, LocalDateTime fechaVenta, List<DetalleVenta> detalleVenta){
        this.idVenta = idVenta;
        this.nombreCliente = nombreCliente;
        this.fechaVenta = fechaVenta;
        this.detalleVenta = detalleVenta;
        this.total = this.detalleVenta.stream().mapToDouble(DetalleVenta::getTotal).sum();
        this.descuentoTotal = this.detalleVenta.stream().mapToDouble(DetalleVenta::getDescuento).sum();
        this.estadoVenta = true;
    }

}