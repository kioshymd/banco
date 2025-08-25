package br.com.desafio.banco.exception;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@ControllerAdvice
public class TratadorDeErros {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validar(MethodArgumentNotValidException ex) {
        Map<String, String> b = new HashMap<>();
        b.put("erro", ex.getBindingResult().getFieldErrors().stream().map(f -> f.getField() + ": " + f.getDefaultMessage()).findFirst().orElse("Requisição inválida"));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(b);
    }
}
