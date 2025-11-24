package com.sistemastarija.api_repuestos.venta.infrastructure.adapter.out.persistance.mapper;

import com.sistemastarija.api_repuestos.venta.domain.model.Venta;
import com.sistemastarija.api_repuestos.venta.infrastructure.adapter.out.persistance.entity.VentaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {DetalleVentaPersistanceMapper.class})
public interface VentaPersistanceMapper {
    @Mapping(target = "id_venta", source = "idVenta")
    @Mapping(target = "descuento_total", source = "descuentoTotal")
    @Mapping(target = "fecha_venta", source = "fechaVenta")
    @Mapping(target = "nombre_cliente", source = "nombreCliente")
    @Mapping(target = "detalle_venta", source = "detalleVenta")
    VentaEntity toVentaEntity(Venta venta);

    @Mapping(target = "idVenta", source = "id_venta")
    @Mapping(target = "descuentoTotal", source = "descuento_total")
    @Mapping(target = "fechaVenta", source = "fecha_venta")
    @Mapping(target = "nombreCliente", source = "nombre_cliente")
    @Mapping(target = "detalleVenta", source = "detalle_venta")
    Venta toVentaModel(VentaEntity entity);


    List<Venta> toListVentaModel(List<VentaEntity> entityList);
}
