package com.sistemastarija.api_repuestos.compra.domain.exception;

public class CompraFailedException extends RuntimeException {
    public CompraFailedException(String message) {
        super(message);
    }

    public CompraFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}

