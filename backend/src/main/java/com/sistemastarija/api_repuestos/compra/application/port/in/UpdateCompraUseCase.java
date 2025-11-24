package com.sistemastarija.api_repuestos.compra.application.port.in;

import com.sistemastarija.api_repuestos.compra.domain.model.Compra;

public interface UpdateCompraUseCase {
    Compra update(Integer idCompra, Compra compra);
}
