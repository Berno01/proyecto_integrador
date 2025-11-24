package com.sistemastarija.api_repuestos.compra.domain.exception;

public class RepuestoFailedException extends RuntimeException {
    public RepuestoFailedException(String message) {
        super(message);
    }
    public RepuestoFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
