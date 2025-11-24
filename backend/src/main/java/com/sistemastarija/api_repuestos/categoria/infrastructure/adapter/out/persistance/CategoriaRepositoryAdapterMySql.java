package com.sistemastarija.api_repuestos.categoria.infrastructure.adapter.out.persistance;

import com.sistemastarija.api_repuestos.categoria.application.port.out.CategoriaPersistantPort;
import com.sistemastarija.api_repuestos.categoria.domain.model.Categoria;
import com.sistemastarija.api_repuestos.categoria.infrastructure.adapter.out.persistance.mapper.CategoriaPersistanceMapper;
import com.sistemastarija.api_repuestos.categoria.infrastructure.adapter.out.persistance.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CategoriaRepositoryAdapterMySql implements CategoriaPersistantPort{

    private final CategoriaRepository repository;
    private final CategoriaPersistanceMapper mapper;

    @Override
    public Categoria save(Categoria categoria) {
        return mapper.toCategoriaModel(repository.save(mapper.toCategoriaEntity(categoria)));
    }

    @Override
    public List<Categoria> findAll() {
        return mapper.toCategoriaList(repository.findAllByEstadoCategoriaTrue());
    }

    @Override
    public Optional<Categoria> findById(Integer idCategoria) {
        return repository.findById(Long.valueOf(idCategoria))
                .map(mapper::toCategoriaModel);
    }
}
