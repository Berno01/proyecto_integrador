package com.sistemastarija.api_repuestos.compra.infrastructure.adapter.in.web.mapper;

import com.sistemastarija.api_repuestos.compra.infrastructure.adapter.in.web.dto.DetalleCompraDTO;
import com.sistemastarija.api_repuestos.compra.infrastructure.adapter.in.web.dto.CompraDTO;
import com.sistemastarija.api_repuestos.compra.domain.model.DetalleCompra;
import com.sistemastarija.api_repuestos.compra.domain.model.Compra;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompraMapper {

    public Compra toDomain(CompraDTO dto) {
        List<DetalleCompra> detalles = dto.getDetalleCompra().stream()
                .map(this::toDetalleCompraDomain)
                .collect(Collectors.toList());

        return new Compra(
                dto.getIdCompra(),
                dto.getNombreProveedor(),
                LocalDateTime.now(), // Se genera automáticamente la fecha
                detalles
        );
    }

    private DetalleCompra toDetalleCompraDomain(DetalleCompraDTO dto) {
        return new DetalleCompra(
                dto.getCantidad(),
                dto.getIdRepuesto(),
                dto.getCostoRepuesto()
        );
    }
}
