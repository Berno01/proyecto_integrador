package com.sistemastarija.api_repuestos.venta.infrastructure.adapter.out.persistance.repository;

import com.sistemastarija.api_repuestos.venta.infrastructure.adapter.out.persistance.entity.VentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VentaRepository extends JpaRepository<VentaEntity, Long> {
    List<VentaEntity> findAllByEstadoVentaTrue();
}
