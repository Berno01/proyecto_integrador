package com.sistemastarija.api_repuestos.venta.infrastructure.adapter.out.persistance;

import com.sistemastarija.api_repuestos.venta.application.port.out.RepuestoPersistantPort;
import com.sistemastarija.api_repuestos.venta.domain.model.Repuesto;
import com.sistemastarija.api_repuestos.venta.infrastructure.adapter.out.persistance.mapper.VentaRepuestoPersistanceMapper;
import com.sistemastarija.api_repuestos.venta.infrastructure.adapter.out.persistance.repository.RepuestoVentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RepuestoRepositoryAdapter implements RepuestoPersistantPort {

    private final VentaRepuestoPersistanceMapper mapper;
    private final RepuestoVentaRepository repository;


    @Override
    public Optional<Repuesto> findById(Integer id_repuesto) {
        return repository.findById(Long.valueOf(id_repuesto))
                .map(mapper::toRepuestoDomain);
    }


    @Override
    public Repuesto save(Repuesto repuesto) {
        return mapper.toRepuestoDomain(repository.save(mapper.toRepuestoEntity(repuesto)));
    }

    @Override
    public List<Repuesto> findAll() {
        return mapper.toRepuestoList(repository.findAllByEstadoRepuestoTrue());
    }
}
