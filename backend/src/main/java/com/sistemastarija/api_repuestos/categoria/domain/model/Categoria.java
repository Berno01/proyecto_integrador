package com.sistemastarija.api_repuestos.categoria.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Categoria {
    private Integer idCategoria;
    private String nombreCategoria;
    private Boolean estadoCategoria;

    public Categoria(Integer idCategoria, String nombreCategoria) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
        this.estadoCategoria = true;
    }
}
