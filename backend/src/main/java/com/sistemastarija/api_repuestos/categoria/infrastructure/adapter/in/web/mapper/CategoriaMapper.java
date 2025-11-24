package com.sistemastarija.api_repuestos.categoria.infrastructure.adapter.in.web.mapper;

import com.sistemastarija.api_repuestos.categoria.domain.model.Categoria;
import com.sistemastarija.api_repuestos.categoria.infrastructure.adapter.in.web.dto.CategoriaDTO;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    public Categoria toCategoriaDomain(CategoriaDTO dto){
        return new Categoria(
                dto.getIdCategoria(),
                dto.getNombreCategoria()
        );
    }

}
