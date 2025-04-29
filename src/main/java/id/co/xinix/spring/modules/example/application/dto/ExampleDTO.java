package id.co.xinix.spring.modules.example.application.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ExampleDTO implements Serializable {

    private Long id ;

    private String code;

    private String name;

    private Integer age;

    private Boolean isDeleted;

    private String createdBy;

    private String updatedBy;

    private Instant createdTime;

    private Instant updatedTime;

    @Min(value = 0)
    @Max(value = 1)
    private Integer status;

    @Size(max = 5000)
    private String value1;

    @Size(max = 5000)
    private String value2;
}
