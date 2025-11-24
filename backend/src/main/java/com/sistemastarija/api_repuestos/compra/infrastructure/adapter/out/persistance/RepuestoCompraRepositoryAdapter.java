package com.sistemastarija.api_repuestos.compra.infrastructure.adapter.out.persistance;

import com.sistemastarija.api_repuestos.compra.infrastructure.adapter.out.persistance.mapper.RepuestoCompraPersistanceMapper;
import com.sistemastarija.api_repuestos.compra.infrastructure.adapter.out.persistance.repository.RepuestoCompraRepository;
import com.sistemastarija.api_repuestos.compra.application.port.out.RepuestoPersistantPort;
import com.sistemastarija.api_repuestos.compra.domain.model.Repuesto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RepuestoCompraRepositoryAdapter implements RepuestoPersistantPort {

    private final RepuestoCompraPersistanceMapper mapper;
    private final RepuestoCompraRepository repository;


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
