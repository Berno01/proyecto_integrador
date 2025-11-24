package com.sistemastarija.api_repuestos.venta.application.port.in;

import com.sistemastarija.api_repuestos.venta.domain.model.Venta;

public interface UpdateVentaUseCase {
    Venta update(Integer idVenta, Venta venta);
}
