package com.sistemastarija.api_repuestos.repuesto.domain.exception;

public class RepuestoException extends RuntimeException {
    public RepuestoException(String message) {
        super(message);
    }

    public RepuestoException(String message, Throwable cause) {
        super(message, cause);
    }
}
