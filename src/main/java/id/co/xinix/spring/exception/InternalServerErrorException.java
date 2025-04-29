package id.co.xinix.spring.exception;

import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends DomainException{

    public InternalServerErrorException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}
