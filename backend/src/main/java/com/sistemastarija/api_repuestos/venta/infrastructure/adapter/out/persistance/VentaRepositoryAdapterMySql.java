package com.sistemastarija.api_repuestos.venta.infrastructure.adapter.out.persistance;


import com.sistemastarija.api_repuestos.venta.application.port.out.VentaPersistantPort;
import com.sistemastarija.api_repuestos.venta.domain.model.Venta;
import com.sistemastarija.api_repuestos.venta.infrastructure.adapter.out.persistance.entity.DetalleVentaEntity;
import com.sistemastarija.api_repuestos.venta.infrastructure.adapter.out.persistance.entity.VentaEntity;
import com.sistemastarija.api_repuestos.venta.infrastructure.adapter.out.persistance.mapper.VentaPersistanceMapper;
import com.sistemastarija.api_repuestos.venta.infrastructure.adapter.out.persistance.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class VentaRepositoryAdapterMySql implements VentaPersistantPort {

    private final VentaRepository repository;
    private final VentaPersistanceMapper mapper;

    @Override
    public Venta save(Venta ventaDominio) {

        VentaEntity ventaEntity = mapper.toVentaEntity(ventaDominio);


        if (ventaEntity.getDetalle_venta() != null) {
            for (DetalleVentaEntity detalle : ventaEntity.getDetalle_venta()) {
                detalle.setVenta(ventaEntity);
            }
        }

        VentaEntity entityGuardada = repository.save(ventaEntity);

        return mapper.toVentaModel(entityGuardada);
    }

    @Override
    public List<Venta> findAll() {

        return mapper.toListVentaModel(repository.findAllByEstadoVentaTrue());
    }

    @Override
    public Optional<Venta> findById(Integer idVenta) {
        return repository.findById(Long.valueOf(idVenta))
                .map(mapper::toVentaModel);
    }



}
