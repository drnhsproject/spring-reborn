package com.sagara.bkn.vac.assessment.exception;

import org.springframework.http.HttpStatus;

public class DomainException extends RuntimeException {

    private final HttpStatus status;

    public DomainException(String message) {
        this(HttpStatus.BAD_REQUEST, message);
    }

    protected DomainException(HttpStatus status, String message) {
        super(message);
        this.status = (status != null) ? status : HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
