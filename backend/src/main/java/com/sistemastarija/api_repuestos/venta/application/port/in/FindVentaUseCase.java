package com.sistemastarija.api_repuestos.venta.application.port.in;

import com.sistemastarija.api_repuestos.venta.domain.model.Venta;

import java.util.List;
import java.util.Optional;

public interface FindVentaUseCase {
    Optional<Venta> findById(Integer id);
    List<Venta> findAll();
}
