package com.sistemastarija.api_repuestos.repuesto.application.port.in;

import com.sistemastarija.api_repuestos.repuesto.domain.model.Repuesto;

public interface UpdateRepuestoUseCase {
    Repuesto update(Repuesto repuesto);
}
