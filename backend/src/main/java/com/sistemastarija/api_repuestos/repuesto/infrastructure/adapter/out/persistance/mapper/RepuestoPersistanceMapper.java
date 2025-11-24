package com.sistemastarija.api_repuestos.repuesto.infrastructure.adapter.out.persistance.mapper;

import com.sistemastarija.api_repuestos.categoria.infrastructure.adapter.out.persistance.entity.CategoriaEntity;
import com.sistemastarija.api_repuestos.repuesto.domain.model.Repuesto;
import com.sistemastarija.api_repuestos.repuesto.infrastructure.adapter.out.persistance.entity.RepuestoEntity;
import com.sistemastarija.api_repuestos.venta.infrastructure.adapter.out.persistance.mapper.DetalleVentaPersistanceMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {DetalleVentaPersistanceMapper.class})
public interface RepuestoPersistanceMapper {
    @Mapping(target = "id_repuesto", source = "idRepuesto")
    @Mapping(target = "nombre_repuesto", source = "nombreRepuesto")
    @Mapping(target = "stock_actual", source = "stockActual")
    @Mapping(target = "costo_repuesto", source = "costoRepuesto")
    @Mapping(target = "precio_sugerido", source = "precioSugerido")
    @Mapping(target = "estadoRepuesto", source = "estadoRepuesto")
    @Mapping(target = "categorias", source = "idsCategorias")

    RepuestoEntity toRepuestoEntity(Repuesto repuesto);

    @Mapping(target = "idRepuesto", source = "id_repuesto")
    @Mapping(target = "nombreRepuesto", source = "nombre_repuesto")
    @Mapping(target = "stockActual", source = "stock_actual")
    @Mapping(target = "costoRepuesto", source = "costo_repuesto")
    @Mapping(target = "precioSugerido", source = "precio_sugerido")
    @Mapping(target = "estadoRepuesto", source = "estadoRepuesto")
    @Mapping(target = "idsCategorias", source = "categorias")
    Repuesto toRepuestoDomain(RepuestoEntity repuestoEntity);

    List<Repuesto> toRepuestoListDomain(List<RepuestoEntity> repuestoEntities);

    default CategoriaEntity map(Integer idCategoria) {
        if (idCategoria == null) return null;
        CategoriaEntity c = new CategoriaEntity();
        c.setIdCategoria(idCategoria); // solo asignamos el ID
        return c;
    }

    default Integer map(CategoriaEntity categoriaEntity) {
        if (categoriaEntity == null) return null;
        return categoriaEntity.getIdCategoria(); // extraemos solo el ID
    }
}
