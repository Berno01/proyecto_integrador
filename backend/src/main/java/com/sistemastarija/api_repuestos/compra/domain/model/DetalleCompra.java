package com.sistemastarija.api_repuestos.compra.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DetalleCompra {

    private Double total;
    private Integer cantidad;
    private Integer idRepuesto;
    private Double costoRepuesto;

    public DetalleCompra(Integer cantidad, Integer idRepuesto, Double costoRepuesto)
    {
        this.cantidad = cantidad;
        this.idRepuesto = idRepuesto;
        this.costoRepuesto = costoRepuesto;
        this.total = this.cantidad * this.costoRepuesto;

    }


}
