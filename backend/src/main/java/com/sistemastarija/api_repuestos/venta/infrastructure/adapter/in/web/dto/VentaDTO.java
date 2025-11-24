package com.sistemastarija.api_repuestos.venta.infrastructure.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class VentaDTO {


    @JsonProperty("id_venta")
    private Integer idVenta;
    @JsonProperty("nombre_cliente")
    private String nombreCliente;
    @JsonProperty("total_venta")
    private Double total;
    @JsonProperty("descuento_total")
    private Double descuentoTotal;

    @JsonProperty("detalle_venta")
    private List<DetalleVentaDTO> detalleVenta;

    public VentaDTO() {
    }


}
