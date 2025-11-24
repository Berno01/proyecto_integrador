package com.sistemastarija.api_repuestos.venta.infrastructure.adapter.in.web.mapper;

import com.sistemastarija.api_repuestos.venta.domain.model.Venta;
import com.sistemastarija.api_repuestos.venta.domain.model.DetalleVenta;
import com.sistemastarija.api_repuestos.venta.infrastructure.adapter.in.web.dto.DetalleVentaDTO;
import com.sistemastarija.api_repuestos.venta.infrastructure.adapter.in.web.dto.VentaDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class VentaMapper {

    public Venta toDomain(VentaDTO dto) {
        List<DetalleVenta> detalles = dto.getDetalleVenta().stream()
                .map(this::toDetalleVentaDomain)
                .collect(Collectors.toList());

        return new Venta(
                dto.getIdVenta(),
                dto.getNombreCliente(),
                LocalDateTime.now(), // Se genera automáticamente la fecha
                detalles
        );
    }

    private DetalleVenta toDetalleVentaDomain(DetalleVentaDTO dto) {
        return new DetalleVenta(
                dto.getCantidad(),
                dto.getPrecioUnitarioRepuesto(),
                dto.getPrecioSugeridoRepuesto(),
                dto.getIdRepuesto(),
                dto.getCostoRepuesto()
        );
    }
}
