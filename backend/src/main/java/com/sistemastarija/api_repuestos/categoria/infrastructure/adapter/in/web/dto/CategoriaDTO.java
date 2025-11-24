package com.sistemastarija.api_repuestos.categoria.infrastructure.adapter.in.web.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CategoriaDTO {

    @JsonProperty("id_categoria")
    private Integer idCategoria;
    @JsonProperty("nombre_categoria")
    private String nombreCategoria;

    public CategoriaDTO() {
    }
}
