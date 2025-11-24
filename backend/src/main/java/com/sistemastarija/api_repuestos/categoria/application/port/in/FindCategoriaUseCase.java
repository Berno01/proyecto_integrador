package com.sistemastarija.api_repuestos.categoria.application.port.in;

import com.sistemastarija.api_repuestos.categoria.domain.model.Categoria;

import java.util.List;
import java.util.Optional;

public interface FindCategoriaUseCase {
    Optional<Categoria> findById(Integer idCategoria);
    List<Categoria> findAll();
}
