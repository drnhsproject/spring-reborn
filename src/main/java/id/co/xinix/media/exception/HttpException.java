package id.co.xinix.media.exception;

import lombok.Getter;

@Getter
public class HttpException extends RuntimeException {

    private final Integer code;

    public HttpException(Integer code, String message) {
        super(message);
        this.code = code;
    }

}
