package com.sistemastarija.api_repuestos.repuesto.infrastructure.adapter.out.persistance;

import com.sistemastarija.api_repuestos.categoria.infrastructure.adapter.out.persistance.repository.CategoriaRepository;
import com.sistemastarija.api_repuestos.repuesto.application.port.out.RepuestoPersistancePort;
import com.sistemastarija.api_repuestos.repuesto.domain.model.Repuesto;
import com.sistemastarija.api_repuestos.repuesto.infrastructure.adapter.out.persistance.mapper.RepuestoPersistanceMapper;
import com.sistemastarija.api_repuestos.repuesto.infrastructure.adapter.out.persistance.entity.RepuestoEntity;
import com.sistemastarija.api_repuestos.repuesto.infrastructure.adapter.out.persistance.repository.RepuestoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RepuestoRepositoryAdapterMysql implements RepuestoPersistancePort {
    private final RepuestoRepository repository;
    private final RepuestoPersistanceMapper mapper;
    private final CategoriaRepository categoriaRepository;

    @Override
    public Repuesto save(Repuesto repuestoDomain) {
        RepuestoEntity repuestoEntity = mapper.toRepuestoEntity(repuestoDomain);
        RepuestoEntity savedRepuestoEntity = repository.save(repuestoEntity);
        return mapper.toRepuestoDomain(savedRepuestoEntity);
    }

    @Override
    public List<Repuesto> findAll() {
        return mapper.toRepuestoListDomain(repository.findAllByEstadoRepuestoTrue());
    }

    @Override
    public Optional<Repuesto> findById(Integer idRepuesto) {
        return repository.findById(idRepuesto).map(mapper::toRepuestoDomain);
    }

    @Override
    public boolean validateCategorias(List<Integer> idsCategorias) {
        if (idsCategorias == null || idsCategorias.isEmpty()) {
            return true;
        }
        long count = categoriaRepository.countByIdCategoriaIn(idsCategorias);
        return count == idsCategorias.size();
    }
}
