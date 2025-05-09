package id.co.xinix.spring.modules.example.application.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExampleCommand {

    private Long id;

    @NotNull(message = "cannot be null")
    @NotBlank(message = "must not be blank")
    private String name;

    @Min(value = 0, message = "must be positive")
    private Integer age;
}
