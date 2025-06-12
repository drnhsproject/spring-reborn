package id.co.xinix.media.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DomainException extends RuntimeException {

    private final HttpStatus status;

    public DomainException(String message) {
        this(HttpStatus.BAD_REQUEST, message);
    }

    protected DomainException(HttpStatus status, String message) {
        super(message);
        this.status = (status != null) ? status : HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
