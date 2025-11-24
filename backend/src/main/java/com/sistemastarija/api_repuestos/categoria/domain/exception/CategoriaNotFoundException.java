package com.sistemastarija.api_repuestos.categoria.domain.exception;

public class CategoriaNotFoundException extends RuntimeException {
    public CategoriaNotFoundException(String message) {
        super(message);
    }
    public CategoriaNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}




