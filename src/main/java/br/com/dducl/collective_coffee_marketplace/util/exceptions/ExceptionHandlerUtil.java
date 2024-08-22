package br.com.dducl.collective_coffee_marketplace.util.exceptions;

import br.com.dducl.collective_coffee_marketplace.util.MessageUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.Clock;
import java.time.Instant;

@ControllerAdvice
public class ExceptionHandlerUtil {

    private final StandardError error;

    private final Clock clock;

    public ExceptionHandlerUtil(Clock clock) {
        this.clock = clock;
        this.error = new StandardError();
    }

    private void setExceptionData(Integer status, String errorDescription, String path, String message) {
        error.setTime(Instant.now(this.clock).atZone(this.clock.getZone()));
        error.setStatus(status);
        error.setError(errorDescription);
        error.setMessage(message);
        error.setPath(path);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(NotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        setExceptionData(status.value(), "Entity Not Found", request.getRequestURI(), e.getMessage());

        return ResponseEntity.status(status).body(this.error);
    }

    @ExceptionHandler(ValidationsException.class)
    public ResponseEntity<StandardError> validationsError(ValidationsException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        setExceptionData(status.value(), "", request.getRequestURI(), e.getMessage());

        return ResponseEntity.status(status).body(this.error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> genericError(Exception e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        setExceptionData(status.value(), "Erro desconhecido", request.getRequestURI(), e.getMessage());

        return ResponseEntity.status(status).body(this.error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<StandardError> dtoValidations(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        String message;

        if (e.getFieldError() != null && e.getFieldError().getDefaultMessage() != null) {
            message = MessageUtil.getMessage(e.getFieldError().getDefaultMessage());

            if (message == null) {
                message = e.getFieldError().getField() + ": " + e.getFieldError().getDefaultMessage();
            }
        } else {
            message = e.getMessage();
        }

        setExceptionData(status.value(),
                "Data Error",
                request.getRequestURI(),
                message
        );

        return ResponseEntity.status(status).body(this.error);
    }
}