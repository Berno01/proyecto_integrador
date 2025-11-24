package com.sistemastarija.api_repuestos.repuesto.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Repuesto {
    private Integer idRepuesto;
    private String nombreRepuesto;
    private Integer stockActual;
    private Double costoRepuesto;
    private Double precioSugerido;
    private Boolean estadoRepuesto;
    private List<Integer> idsCategorias;

}
