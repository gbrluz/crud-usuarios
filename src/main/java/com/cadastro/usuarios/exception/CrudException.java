package com.cadastro.usuarios.exception;

import org.springframework.http.HttpStatus;

public class CrudException extends RuntimeException{
    protected HttpStatus status;

    public CrudException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
