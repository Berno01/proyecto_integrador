package com.sistemastarija.api_repuestos.compra.infrastructure.adapter.out.persistance.mapper;

import com.sistemastarija.api_repuestos.compra.infrastructure.adapter.out.persistance.entity.CompraEntity;
import com.sistemastarija.api_repuestos.compra.domain.model.Compra;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {DetalleCompraPersistanceMapper.class})
public interface CompraPersistanceMapper {
    @Mapping(target = "id_compra", source = "idCompra")
    @Mapping(target = "fecha_compra", source = "fechaCompra")
    @Mapping(target = "nombre_proveedor", source = "nombreProveedor")
    @Mapping(target = "detalle_compra", source = "detalleCompra")
    CompraEntity toCompraEntity(Compra compra);

    @Mapping(target = "idCompra", source = "id_compra")
    @Mapping(target = "fechaCompra", source = "fecha_compra")
    @Mapping(target = "nombreProveedor", source = "nombre_proveedor")
    @Mapping(target = "detalleCompra", source = "detalle_compra")
    Compra toCompraModel(CompraEntity entity);


    List<Compra> toListCompraModel(List<CompraEntity> entityList);
}
