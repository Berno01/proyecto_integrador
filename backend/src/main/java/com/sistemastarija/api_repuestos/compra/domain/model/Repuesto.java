package com.sistemastarija.api_repuestos.compra.domain.model;

import com.sistemastarija.api_repuestos.venta.domain.exception.RepuestoNotFoundException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Repuesto {
    private Integer idRepuesto;
    private Integer stockRepuesto;
    private String nombreRepuesto;
    private Double costoRepuesto;
    private Double precioSugerido;
    private Boolean estadoRepuesto;

    public Repuesto(Integer idRepuesto, Integer stockRepuesto) {
        this.idRepuesto = idRepuesto;
        this.stockRepuesto = stockRepuesto;
        this.estadoRepuesto = true;
    }

    public boolean decreaseStockRepuesto(Integer cantidad) {

        if (this.stockRepuesto < cantidad) {
            throw new RepuestoNotFoundException("Stock insuficiente para el repuesto ID " + this.idRepuesto +
                    ". Solicitado: " + cantidad + ", Disponible: " + this.stockRepuesto);
        } this.stockRepuesto -= cantidad;

        return true;
    }

    public boolean encreaseStockRepuesto(Integer cantidad) {

        this.stockRepuesto += cantidad;
        return true;
    }

}


