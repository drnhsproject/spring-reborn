package id.co.xinix.spring.exception;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
        HttpStatus status = ex.getStatus() != null ? ex.getStatus() : HttpStatus.INTERNAL_SERVER_ERROR;

        ErrorExceptionResponse errorResponse = new ErrorExceptionResponse(
                status.value(),
                ex.getMessage()
        );

        return new ResponseEntity<>(errorResponse, status);
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

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorExceptionResponse> handleJwtException(JwtException ex) {
        ErrorExceptionResponse error = new ErrorExceptionResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "Invalid or expired token: " + ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorExceptionResponse> handleAuthException(UnauthorizedException ex) {
        ErrorExceptionResponse error = new ErrorExceptionResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized: " + ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorExceptionResponse> handleJsonParseError(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorExceptionResponse(400, "Invalid request body: " + ex.getMessage()));
    }

}
