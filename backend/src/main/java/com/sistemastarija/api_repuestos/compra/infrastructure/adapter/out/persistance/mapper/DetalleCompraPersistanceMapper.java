package com.sistemastarija.api_repuestos.compra.infrastructure.adapter.out.persistance.mapper;
import com.sistemastarija.api_repuestos.compra.infrastructure.adapter.out.persistance.entity.DetalleCompraEntity;
import com.sistemastarija.api_repuestos.compra.domain.model.DetalleCompra;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DetalleCompraPersistanceMapper {

    @Mapping(target = "id_repuesto", source = "idRepuesto")
    @Mapping(target = "costo_repuesto", source = "costoRepuesto")
    @Mapping(target = "id_detalle_compra", ignore = true) // La ID se genera en la DB
    @Mapping(target = "compra", ignore = true)
    DetalleCompraEntity toDetalleEntity(DetalleCompra detalle);


    @Mapping(target = "idRepuesto", source = "id_repuesto")
    @Mapping(target = "costoRepuesto", source = "costo_repuesto")
    DetalleCompra toDetalleModel(DetalleCompraEntity entity);
}