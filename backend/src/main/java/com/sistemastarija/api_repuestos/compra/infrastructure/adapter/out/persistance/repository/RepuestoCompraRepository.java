package com.sistemastarija.api_repuestos.compra.infrastructure.adapter.out.persistance.repository;

import com.sistemastarija.api_repuestos.compra.infrastructure.adapter.out.persistance.entity.RepuestoCompraEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepuestoCompraRepository extends JpaRepository<RepuestoCompraEntity, Long> {
    List<RepuestoCompraEntity> findAllByEstadoRepuestoTrue();
}
