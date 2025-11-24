package com.sistemastarija.api_repuestos.compra.infrastructure.adapter.out.persistance.mapper;

import com.sistemastarija.api_repuestos.compra.infrastructure.adapter.out.persistance.entity.RepuestoCompraEntity;
import com.sistemastarija.api_repuestos.compra.domain.model.Repuesto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RepuestoCompraPersistanceMapper {

    @Mapping(target = "stock_actual", source = "stockRepuesto")
    @Mapping(target = "id_repuesto", source = "idRepuesto")
    @Mapping(target = "nombre_repuesto", source = "nombreRepuesto")
    @Mapping(target = "costo_repuesto", source = "costoRepuesto")
    @Mapping(target = "precio_sugerido", source = "precioSugerido")
    RepuestoCompraEntity toRepuestoEntity(Repuesto repuesto);

    @Mapping(target = "stockRepuesto", source = "stock_actual")
    @Mapping(target = "idRepuesto", source = "id_repuesto")

    @Mapping(target = "nombreRepuesto", source = "nombre_repuesto")
    @Mapping(target = "costoRepuesto", source = "costo_repuesto")
    @Mapping(target = "precioSugerido", source = "precio_sugerido")


    Repuesto toRepuestoDomain(RepuestoCompraEntity repuestoCompraEntity);

    List<Repuesto> toRepuestoList(List<RepuestoCompraEntity> entityList);

}
