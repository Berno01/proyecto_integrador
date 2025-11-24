package com.sistemastarija.api_repuestos.venta.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DetalleVenta {

    private Double total;
    private Integer cantidad;
    private Double precioUnitarioRepuesto;
    private Double precioSugeridoRepuesto;
    private Double descuento;
    private Integer idRepuesto;
    private Double costoRepuesto;

    public DetalleVenta(Integer cantidad, Double precioUnitarioRepuesto, Double precioSugeridoRepuesto, Integer idRepuesto, Double costoRepuesto)
    {
        this.cantidad = cantidad;
        this.precioUnitarioRepuesto = precioUnitarioRepuesto;
        this.precioSugeridoRepuesto = precioSugeridoRepuesto;
        this.idRepuesto = idRepuesto;
        this.costoRepuesto = costoRepuesto;
        this.total = this.cantidad * this.precioUnitarioRepuesto;

        if(this.precioSugeridoRepuesto - this.precioUnitarioRepuesto <0)
            this.descuento = 0.0;
        else this.descuento =  this.precioSugeridoRepuesto - this.precioUnitarioRepuesto;
    }


}
