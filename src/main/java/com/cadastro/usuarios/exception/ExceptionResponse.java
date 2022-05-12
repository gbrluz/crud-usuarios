package com.cadastro.usuarios.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@Builder
public class ExceptionResponse {
    private String name;
    private Object cause;
    private LocalDateTime timestamp;
    private HttpStatus httpStatus;
}
