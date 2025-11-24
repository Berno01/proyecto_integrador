package com.sistemastarija.api_repuestos.categoria.application.port.out;

import com.sistemastarija.api_repuestos.categoria.domain.model.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaPersistantPort {
    Categoria save(Categoria categoria);
    List<Categoria> findAll();
    Optional<Categoria> findById(Integer idCategoria);
}
