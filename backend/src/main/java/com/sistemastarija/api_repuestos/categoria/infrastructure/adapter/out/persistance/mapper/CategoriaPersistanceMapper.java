package com.sistemastarija.api_repuestos.categoria.infrastructure.adapter.out.persistance.mapper;

import com.sistemastarija.api_repuestos.categoria.domain.model.Categoria;
import com.sistemastarija.api_repuestos.categoria.infrastructure.adapter.out.persistance.entity.CategoriaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface CategoriaPersistanceMapper {
    @Mapping(target = "nombre_categoria", source = "nombreCategoria")
    CategoriaEntity toCategoriaEntity(Categoria categoria);


    @Mapping(target = "nombreCategoria", source = "nombre_categoria")
    Categoria toCategoriaModel(CategoriaEntity categoriaEntity);

    List<Categoria> toCategoriaList(List<CategoriaEntity> categoriaEntities);
}
