package com.cadastro.usuarios.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GenericHandler {

    @ExceptionHandler(CrudException.class)
    public ResponseEntity<ExceptionResponse> handlerCrudExceptionException(CrudException exception) {
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .name("CrudException")
                .cause(exception.getMessage())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
