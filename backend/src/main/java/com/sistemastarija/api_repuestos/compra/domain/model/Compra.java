package com.sistemastarija.api_repuestos.compra.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class Compra
{
    private Integer idCompra;
    private String nombreProveedor;
    private LocalDateTime fechaCompra;
    private Double total;
    private List<DetalleCompra> detalleCompra;
    private Boolean estadoCompra;

    public Compra(Integer idCompra, String nombreProveedor, LocalDateTime fechaCompra, List<DetalleCompra> detalleCompra){
        this.idCompra = idCompra;
        this.nombreProveedor = nombreProveedor;
        this.fechaCompra = fechaCompra;
        this.detalleCompra = detalleCompra;
        this.total = this.detalleCompra.stream().mapToDouble(DetalleCompra::getTotal).sum();
        this.estadoCompra = true;
    }

}