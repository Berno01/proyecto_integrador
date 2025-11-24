package com.sistemastarija.api_repuestos.venta.application.port.out;

import com.sistemastarija.api_repuestos.venta.domain.model.Venta;

import java.util.List;
import java.util.Optional;

public interface VentaPersistantPort {
    Venta save(Venta venta);
    List<Venta> findAll();
    Optional<Venta> findById(Integer id);
}