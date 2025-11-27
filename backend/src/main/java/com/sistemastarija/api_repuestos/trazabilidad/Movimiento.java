package com.sistemastarija.api_repuestos.trazabilidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

@Entity
@Immutable
@Table(name = "vista_trazabilidad")
@Data
public class Movimiento {

    @Id
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fecha;

    private String producto;

    @Column(name = "tipo_movimiento")
    private String tipoMovimiento;

    private Integer cantidad;
}
