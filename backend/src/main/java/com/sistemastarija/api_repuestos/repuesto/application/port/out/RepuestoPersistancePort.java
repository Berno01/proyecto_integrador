package com.sistemastarija.api_repuestos.repuesto.application.port.out;

import com.sistemastarija.api_repuestos.repuesto.domain.model.Repuesto;

import java.util.List;
import java.util.Optional;

public interface RepuestoPersistancePort {
    Repuesto save(Repuesto repuesto);
    List<Repuesto> findAll();
    Optional<Repuesto> findById(Integer idRepuesto);

    boolean validateCategorias(List<Integer> idsCategorias);
}
