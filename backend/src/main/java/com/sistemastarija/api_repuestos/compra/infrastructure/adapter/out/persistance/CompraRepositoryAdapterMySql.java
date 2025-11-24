package com.sistemastarija.api_repuestos.compra.infrastructure.adapter.out.persistance;


import com.sistemastarija.api_repuestos.compra.infrastructure.adapter.out.persistance.entity.DetalleCompraEntity;
import com.sistemastarija.api_repuestos.compra.infrastructure.adapter.out.persistance.entity.CompraEntity;
import com.sistemastarija.api_repuestos.compra.infrastructure.adapter.out.persistance.mapper.CompraPersistanceMapper;
import com.sistemastarija.api_repuestos.compra.infrastructure.adapter.out.persistance.repository.CompraRepository;
import com.sistemastarija.api_repuestos.compra.application.port.out.CompraPersistantPort;
import com.sistemastarija.api_repuestos.compra.domain.model.Compra;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CompraRepositoryAdapterMySql implements CompraPersistantPort {

    private final CompraRepository repository;
    private final CompraPersistanceMapper mapper;

    @Override
    public Compra save(Compra compraDominio) {

        CompraEntity compraEntity = mapper.toCompraEntity(compraDominio);


        if (compraEntity.getDetalle_compra() != null) {
            for (DetalleCompraEntity detalle : compraEntity.getDetalle_compra()) {
                detalle.setCompra(compraEntity);
            }
        }

        CompraEntity entityGuardada = repository.save(compraEntity);

        return mapper.toCompraModel(entityGuardada);
    }

    @Override
    public List<Compra> findAll() {

        return mapper.toListCompraModel(repository.findAllByEstadoCompraTrue());
    }

    @Override
    public Optional<Compra> findById(Integer idCompra) {
        return repository.findById(Long.valueOf(idCompra))
                .map(mapper::toCompraModel);
    }



}
