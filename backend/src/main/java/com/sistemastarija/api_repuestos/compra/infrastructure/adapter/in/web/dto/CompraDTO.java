package com.sistemastarija.api_repuestos.compra.infrastructure.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CompraDTO {


    @JsonProperty("id_compra")
    private Integer idCompra;
    @JsonProperty("nombre_proveedor")
    private String nombreProveedor;
    @JsonProperty("total_compra")
    private Double total;

    @JsonProperty("detalle_compra")
    private List<DetalleCompraDTO> detalleCompra;

    public CompraDTO() {
    }


}
