package com.sistemastarija.api_repuestos.repuesto.infrastructure.adapter.out.persistance.repository;

import com.sistemastarija.api_repuestos.repuesto.infrastructure.adapter.out.persistance.entity.RepuestoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepuestoRepository extends JpaRepository<RepuestoEntity, Integer> {

    List<RepuestoEntity> findAllByEstadoRepuestoTrue();
}
