package com.sistemastarija.api_repuestos.compra.application.port.in;

import com.sistemastarija.api_repuestos.compra.domain.model.Repuesto;

import java.util.List;
import java.util.Optional;

public interface RepuestoUseCase {
    List<com.sistemastarija.api_repuestos.compra.domain.model.Repuesto> findAll();
    Optional<Repuesto> findById(Integer id);
}