package id.co.xinix.spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Component("coreErrorExceptionHandler")
public class ErrorExceptionHandler {

    @ExceptionHandler(HttpException.class)
    public ResponseEntity<ErrorExceptionResponse> handleHttpException(HttpException ex) {
        ErrorExceptionResponse errorResponse = new ErrorExceptionResponse(ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ex.getCode()));
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorExceptionResponse> handleDomainException(DomainException ex) {
        ErrorExceptionResponse errorResponse = new ErrorExceptionResponse(ex.getStatus().value(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorExceptionResponse> handleAccessDeniedException(AccessDeniedException ex) {
        ErrorExceptionResponse errorResponse = new ErrorExceptionResponse(
                HttpStatus.FORBIDDEN.value(),
                ex.getMessage() != null ? ex.getMessage() : "Access denied"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorValidationExceptionResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        ErrorValidationExceptionResponse errorResponse = new ErrorValidationExceptionResponse(
                400,
                "validation failed",
                errors
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
