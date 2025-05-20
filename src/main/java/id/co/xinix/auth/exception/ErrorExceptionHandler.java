package id.co.xinix.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice("id.co.xinix.auth.modules")
@Component("authErrorExceptionHandler")
public class ErrorExceptionHandler {

    @ExceptionHandler(HttpException.class)
    public ResponseEntity<ErrorExceptionResponse> handleHttpException(HttpException ex) {
        ErrorExceptionResponse errorResponse = new ErrorExceptionResponse(ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ex.getCode()));
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorExceptionResponse> handleDomainException(DomainException ex) {
        return new ResponseEntity<>(new ErrorExceptionResponse(ex), ex.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorExceptionResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ErrorDetail> errorDetails = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new ErrorDetail( 400, error.getField() + ": " + error.getDefaultMessage()))
                .toList();

        return ResponseEntity.badRequest().body(new ErrorExceptionResponse(errorDetails));
    }
}
