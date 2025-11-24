package com.sistemastarija.api_repuestos.venta.application.port.in;

import com.sistemastarija.api_repuestos.venta.domain.model.Repuesto;

import java.util.List;
import java.util.Optional;

public interface RepuestoUseCase {
    List<Repuesto> findAll();
    Optional<Repuesto> findById(Integer id);
}