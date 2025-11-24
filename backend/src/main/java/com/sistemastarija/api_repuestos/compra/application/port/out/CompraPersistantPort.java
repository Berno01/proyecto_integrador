package com.sistemastarija.api_repuestos.compra.application.port.out;

import com.sistemastarija.api_repuestos.compra.domain.model.Compra;

import java.util.List;
import java.util.Optional;

public interface CompraPersistantPort {
    Compra save(Compra compra);
    List<Compra> findAll();
    Optional<Compra> findById(Integer idCompra);
}