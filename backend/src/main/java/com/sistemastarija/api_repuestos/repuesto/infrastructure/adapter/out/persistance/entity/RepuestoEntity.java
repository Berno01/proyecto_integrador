package com.sistemastarija.api_repuestos.repuesto.infrastructure.adapter.out.persistance.entity;

import com.sistemastarija.api_repuestos.categoria.infrastructure.adapter.out.persistance.entity.CategoriaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "repuesto")
public class RepuestoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_repuesto;

    private String nombre_repuesto;
    private Integer stock_actual;
    private Double costo_repuesto;
    private Double precio_sugerido;
    private Boolean estadoRepuesto;

    @ManyToMany
    @JoinTable(
            name = "repuesto_categoria",
            joinColumns = @JoinColumn(name = "id_repuesto"),
            inverseJoinColumns = @JoinColumn(name = "id_categoria")
    )
    private List<CategoriaEntity> categorias;
}
