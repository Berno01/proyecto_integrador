package com.sistemastarija.api_repuestos.compra.application.port.in;

import com.sistemastarija.api_repuestos.compra.domain.model.Compra;

import java.util.List;
import java.util.Optional;

public interface FindCompraUseCase {
    Optional<Compra> findById(Integer id);
    List<Compra> findAll();
}
