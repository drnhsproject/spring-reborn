package id.co.xinix.auth.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends DomainException {

    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
