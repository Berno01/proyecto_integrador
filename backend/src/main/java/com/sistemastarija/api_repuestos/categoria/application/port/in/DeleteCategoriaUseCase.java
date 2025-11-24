package com.sistemastarija.api_repuestos.categoria.application.port.in;

import com.sistemastarija.api_repuestos.categoria.domain.model.Categoria;

public interface DeleteCategoriaUseCase {
    Boolean delete(Integer idCategoria);
}
