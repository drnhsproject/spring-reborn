package com.sagara.bkn.vac.assessment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
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
}
