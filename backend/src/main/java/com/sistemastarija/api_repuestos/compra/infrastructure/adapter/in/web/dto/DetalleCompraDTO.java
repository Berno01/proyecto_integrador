package com.sistemastarija.api_repuestos.compra.infrastructure.adapter.in.web.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DetalleCompraDTO {
    @JsonProperty("total")
    private Double total;

    @JsonProperty("cantidad")
    private Integer cantidad;


    @JsonProperty("id_repuesto")
    private Integer idRepuesto;

    @JsonProperty("costo_repuesto")
    private Double costoRepuesto;





    public DetalleCompraDTO() {
    }


}
