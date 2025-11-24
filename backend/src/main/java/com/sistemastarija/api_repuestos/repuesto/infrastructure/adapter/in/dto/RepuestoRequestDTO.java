package com.sistemastarija.api_repuestos.repuesto.infrastructure.adapter.in.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RepuestoRequestDTO {
    @JsonProperty("id_repuesto")
    private Integer idRepuesto;

    @JsonProperty("nombre_repuesto")
    private String nombreRepuesto;

    @JsonProperty("stock_actual")
    private Integer stockActual;
    @JsonProperty("costo_repuesto")
    private Double costoRepuesto;
    @JsonProperty("precio_sugerido")
    private Double precioSugerido;
    @JsonProperty("estado_repuesto")
    private Boolean estadoRepuesto;
    @JsonProperty("ids_categorias")
    private List<Integer> idsCategorias;

    public RepuestoRequestDTO(){}
}
