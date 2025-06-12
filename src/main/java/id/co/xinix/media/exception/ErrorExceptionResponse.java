package id.co.xinix.media.exception;

import java.util.List;

public record ErrorExceptionResponse(List<ErrorDetail> errors) {
    public ErrorExceptionResponse(int status, String message) {
        this(List.of(new ErrorDetail(status, message)));
    }

    public ErrorExceptionResponse(DomainException ex) {
        this(List.of(new ErrorDetail(ex.getStatus().value(), ex.getMessage())));
    }
}
