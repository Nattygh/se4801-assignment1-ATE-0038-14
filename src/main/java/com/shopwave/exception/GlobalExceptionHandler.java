//ID ATE/0038/14

package com.shopwave.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleNotFound(
            ProductNotFoundException ex,
            jakarta.servlet.http.HttpServletRequest request) {

        return Map.of(
                "timestamp", LocalDateTime.now(),
                "status", 404,
                "error", "Not Found",
                "message", ex.getMessage(),
                "path", request.getRequestURI()
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleBadRequest(
            IllegalArgumentException ex,
            jakarta.servlet.http.HttpServletRequest request) {

        return Map.of(
                "timestamp", LocalDateTime.now(),
                "status", 400,
                "error", "Bad Request",
                "message", ex.getMessage(),
                "path", request.getRequestURI()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleGeneric(
            Exception ex,
            jakarta.servlet.http.HttpServletRequest request) {

        return Map.of(
                "timestamp", LocalDateTime.now(),
                "status", 400,
                "error", "Error",
                "message", ex.getMessage(),
                "path", request.getRequestURI()
        );
    }
}