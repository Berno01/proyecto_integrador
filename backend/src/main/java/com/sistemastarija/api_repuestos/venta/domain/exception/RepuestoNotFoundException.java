package com.sistemastarija.api_repuestos.venta.domain.exception;

public class RepuestoNotFoundException extends RuntimeException {

    public RepuestoNotFoundException(String message) {
        super(message);
    }


    public RepuestoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}