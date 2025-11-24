package com.sistemastarija.api_repuestos.venta.infrastructure.adapter.out.persistance.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "repuesto")
public class VentaRepuestoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_repuesto;
    private Integer stock_actual;
    private String nombre_repuesto;
    private Integer costo_repuesto;
    private Double precio_sugerido;
    private Boolean estadoRepuesto;
}
