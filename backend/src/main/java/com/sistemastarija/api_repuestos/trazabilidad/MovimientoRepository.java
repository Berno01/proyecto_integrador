package com.sistemastarija.api_repuestos.trazabilidad;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, LocalDateTime> {
}
