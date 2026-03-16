package io.codepred.task.exception;

import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for the web layer.
 * <p>
 * Provides centralized exception handling across all @RequestMapping methods.
 * Converts specific exceptions into appropriate HTTP responses with error details.
 */
@RestControllerAdvice
public class WebExceptionHandler {
    /**
     * Handles validation exceptions for request bodies.
     *
     * @param exception the validation exception containing binding errors
     * @return a map of field names to error messages with a 400 Bad Request status
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, @Nullable String>> handleValidationException(MethodArgumentNotValidException exception) {
        Map<String, @Nullable String> errors = new HashMap<>();
        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}