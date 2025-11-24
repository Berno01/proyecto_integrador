package com.sistemastarija.api_repuestos.repuesto.application.service;
import com.sistemastarija.api_repuestos.repuesto.application.port.in.*;
import com.sistemastarija.api_repuestos.repuesto.application.port.out.RepuestoPersistancePort;
import com.sistemastarija.api_repuestos.repuesto.domain.exception.RepuestoException;
import com.sistemastarija.api_repuestos.repuesto.domain.model.Repuesto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RepuestoService implements CreateRepuestoUseCase, UpdateRepuestoUseCase, FindRepuestoUseCase, DeleteRepuestoUseCase {
    private final RepuestoPersistancePort repuestoPersistancePort;


    @Transactional
    @Override
    public Repuesto save(Repuesto repuesto) {
        if (!repuestoPersistancePort.validateCategorias(repuesto.getIdsCategorias())) {
            throw new RepuestoException("Una o más categorías no existen");
        }
        return repuestoPersistancePort.save(repuesto);
    }

    @Override
    public boolean delete(Integer idRepuesto) {
        Optional<Repuesto> repuestoOp = repuestoPersistancePort.findById(idRepuesto);
        Repuesto repuestoDb = repuestoOp.orElseThrow(() ->
                new RepuestoException("Repuesto no encontrado."));
        repuestoDb.setEstadoRepuesto(false);
        repuestoPersistancePort.save(repuestoDb);
        return true;
    }

    @Override
    public Optional<Repuesto> findById(Integer idRepuesto) {
        return repuestoPersistancePort.findById(idRepuesto);
    }

    @Override
    public List<Repuesto> findAll() {
        return repuestoPersistancePort.findAll();
    }

    @Override
    public Repuesto update(Repuesto repuesto) {
        Optional<Repuesto> repuestoOp = repuestoPersistancePort.findById(repuesto.getIdRepuesto());
        Repuesto repuestoDb = repuestoOp.orElseThrow(() ->
                new RepuestoException("Repuesto no encontrado."));

        repuestoDb.setNombreRepuesto(repuesto.getNombreRepuesto());
        repuestoDb.setStockActual(repuesto.getStockActual());
        repuestoDb.setCostoRepuesto(repuesto.getCostoRepuesto());
        repuestoDb.setPrecioSugerido(repuesto.getPrecioSugerido());
        repuestoDb.setIdsCategorias(repuesto.getIdsCategorias());
        return repuestoPersistancePort.save(repuestoDb);
    }
}
