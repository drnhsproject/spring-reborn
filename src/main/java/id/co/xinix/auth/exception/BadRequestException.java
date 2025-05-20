package id.co.xinix.auth.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends DomainException {

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
