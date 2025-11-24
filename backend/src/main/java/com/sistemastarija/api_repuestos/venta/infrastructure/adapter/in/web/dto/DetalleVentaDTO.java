package com.sistemastarija.api_repuestos.venta.infrastructure.adapter.in.web.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DetalleVentaDTO {
    @JsonProperty("total")
    private Double total;

    @JsonProperty("cantidad")
    private Integer cantidad;

    @JsonProperty("precio_unitario_repuesto")
    private Double precioUnitarioRepuesto;

    @JsonProperty("precio_sugerido_repuesto")
    private Double precioSugeridoRepuesto;

    @JsonProperty("id_repuesto")
    private Integer idRepuesto;

    @JsonProperty("costo_repuesto")
    private Double costoRepuesto;





    public DetalleVentaDTO() {
    }


}
