package com.sagara.bkn.vac.assessment.exception;

public class HttpException extends RuntimeException {

    private final Integer code;

    public HttpException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
