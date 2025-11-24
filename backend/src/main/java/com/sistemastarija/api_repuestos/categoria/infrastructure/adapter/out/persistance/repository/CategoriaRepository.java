package com.sistemastarija.api_repuestos.categoria.infrastructure.adapter.out.persistance.repository;

import com.sistemastarija.api_repuestos.categoria.infrastructure.adapter.out.persistance.entity.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long> {
    List<CategoriaEntity> findAllByEstadoCategoriaTrue();

    boolean countByIdCategoriaIn(Collection<Integer> idCategorias);

    long countByIdCategoriaIn(List<Integer> idCategorias);
}
