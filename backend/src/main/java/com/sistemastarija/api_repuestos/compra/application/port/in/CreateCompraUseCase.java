package com.sistemastarija.api_repuestos.compra.application.port.in;

import com.sistemastarija.api_repuestos.compra.domain.model.Compra;

public interface CreateCompraUseCase {

    Compra save(Compra venta);

}
