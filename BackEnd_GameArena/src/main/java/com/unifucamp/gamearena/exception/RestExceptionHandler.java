package com.unifucamp.gamearena.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(GameArenaException.class)
    public ProblemDetail handleGameArenaException(GameArenaException e) {
        return e.toProblemDetail();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        var fieldErrors = e.getFieldErrors()
                .stream()
                .map(f -> new FieldError(f.getField(), f.getDefaultMessage()))
                .toList();

        var pb = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        pb.setTitle("Erro de validação nos campos da requisição");
        pb.setDetail("Um ou mais campos estão com valores inválidos.");
        pb.setProperty("errors", fieldErrors);

        return pb;
    }

    private record FieldError(
            String name,
            String reason
    ){}
}
