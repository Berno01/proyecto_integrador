package com.sistemastarija.api_repuestos.repuesto.infrastructure.adapter.in.mapper;

import com.sistemastarija.api_repuestos.repuesto.domain.model.Repuesto;
import com.sistemastarija.api_repuestos.repuesto.infrastructure.adapter.in.dto.RepuestoRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class RepuestoMapper {
    public Repuesto toDomain(RepuestoRequestDTO dto){
        return new Repuesto(
                dto.getIdRepuesto(),
                dto.getNombreRepuesto(),
                dto.getStockActual(),
                dto.getCostoRepuesto(),
                dto.getPrecioSugerido(),
                dto.getEstadoRepuesto(),
                dto.getIdsCategorias()
        );
    }
}
