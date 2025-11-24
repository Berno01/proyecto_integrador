package com.sistemastarija.api_repuestos.venta.infrastructure.adapter.out.persistance.repository;

import com.sistemastarija.api_repuestos.venta.infrastructure.adapter.out.persistance.entity.VentaRepuestoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepuestoVentaRepository extends JpaRepository<VentaRepuestoEntity, Long> {
    List<VentaRepuestoEntity> findAllByEstadoRepuestoTrue();
}
