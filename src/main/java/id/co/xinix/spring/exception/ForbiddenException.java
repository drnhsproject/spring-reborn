package id.co.xinix.spring.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends DomainException {

    public ForbiddenException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}
