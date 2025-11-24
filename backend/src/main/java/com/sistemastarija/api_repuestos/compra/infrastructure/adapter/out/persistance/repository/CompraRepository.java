package com.sistemastarija.api_repuestos.compra.infrastructure.adapter.out.persistance.repository;

import com.sistemastarija.api_repuestos.compra.infrastructure.adapter.out.persistance.entity.CompraEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompraRepository extends JpaRepository<CompraEntity, Long> {
    List<CompraEntity> findAllByEstadoCompraTrue();
}
