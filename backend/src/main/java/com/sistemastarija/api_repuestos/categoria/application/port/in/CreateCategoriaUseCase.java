package com.sistemastarija.api_repuestos.categoria.application.port.in;

import com.sistemastarija.api_repuestos.categoria.domain.model.Categoria;

public interface CreateCategoriaUseCase {
    Categoria save(Categoria categoria);
}
