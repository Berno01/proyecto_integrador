package com.sistemastarija.api_repuestos.repuesto.application.port.in;

import com.sistemastarija.api_repuestos.repuesto.domain.model.Repuesto;

public interface DeleteRepuestoUseCase {
    boolean delete(Integer idRepuesto);
}
