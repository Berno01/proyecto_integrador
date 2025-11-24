package com.sistemastarija.api_repuestos.compra.application.service;

import com.sistemastarija.api_repuestos.compra.application.port.in.RepuestoUseCase;
import com.sistemastarija.api_repuestos.compra.application.port.out.RepuestoPersistantPort;
import com.sistemastarija.api_repuestos.compra.domain.model.Repuesto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RepuestoCompraService implements RepuestoUseCase {

    private final RepuestoPersistantPort persistencetPort;

    @Override
    public List<Repuesto> findAll() {
        return persistencetPort.findAll();
    }

    @Override
    public Optional<Repuesto> findById(Integer idRepuesto) {
        Optional<Repuesto> opcionalRepuesto = persistencetPort.findById(idRepuesto);
        return opcionalRepuesto;
    }
}
