package com.sistemastarija.api_repuestos.repuesto.application.port.in;

import com.sistemastarija.api_repuestos.repuesto.domain.model.Repuesto;

import java.util.List;
import java.util.Optional;

public interface FindRepuestoUseCase {
    Optional<Repuesto> findById(Integer idRepuesto);
    List<Repuesto> findAll();
}
