package id.co.xinix.spring.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class ErrorValidationExceptionResponse {
    private int status;
    private String message;
    private Map<String, String> errors;
}
