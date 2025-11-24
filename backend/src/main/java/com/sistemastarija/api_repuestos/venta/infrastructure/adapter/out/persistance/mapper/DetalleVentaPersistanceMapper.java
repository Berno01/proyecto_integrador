package com.sistemastarija.api_repuestos.venta.infrastructure.adapter.out.persistance.mapper;
import com.sistemastarija.api_repuestos.venta.domain.model.DetalleVenta;
import com.sistemastarija.api_repuestos.venta.infrastructure.adapter.out.persistance.entity.DetalleVentaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DetalleVentaPersistanceMapper {

    @Mapping(target = "precio_unitario_repuesto", source = "precioUnitarioRepuesto")
    @Mapping(target = "id_repuesto", source = "idRepuesto")
    @Mapping(target = "precio_sugerido_repuesto", source = "precioSugeridoRepuesto")
    @Mapping(target = "costo_repuesto", source = "costoRepuesto")


    @Mapping(target = "id_detalle_venta", ignore = true) // La ID se genera en la DB
    @Mapping(target = "venta", ignore = true)
    DetalleVentaEntity toDetalleEntity(DetalleVenta detalle);

    @Mapping(target = "precioUnitarioRepuesto", source = "precio_unitario_repuesto")
    @Mapping(target = "idRepuesto", source = "id_repuesto")
    @Mapping(target = "precioSugeridoRepuesto", source = "precio_sugerido_repuesto")
    @Mapping(target = "costoRepuesto", source = "costo_repuesto")
    DetalleVenta toDetalleModel(DetalleVentaEntity entity);
}