package com.sistemastarija.api_repuestos.compra.application.port.out;

import com.sistemastarija.api_repuestos.compra.domain.model.Repuesto;

import java.util.List;
import java.util.Optional;

public interface RepuestoPersistantPort {
    Optional<Repuesto> findById(Integer id_repuesto);
    Repuesto save(Repuesto repuesto);
    List<Repuesto> findAll();
}
