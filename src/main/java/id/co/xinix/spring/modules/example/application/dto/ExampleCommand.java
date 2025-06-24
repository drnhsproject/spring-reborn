package id.co.xinix.spring.modules.example.application.dto;

import id.co.xinix.media.modules.MediaDataResult;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ExampleCommand {

    private Long id;

    private String code;

    @NotNull(message = "cannot be null")
    @NotBlank(message = "must not be blank")
    private String name;

    @NotNull(message = "cannot be null")
    @NotBlank(message = "must not be blank")
    private String nik;

    @NotNull(message = "cannot be null")
    @Size(min = 1, message = "must not be empty")
    private List<String> hobbies;

    @Min(value = 0, message = "must be positive")
    private Integer citizen;

    @NotNull(message = "cannot be null")
    @NotBlank(message = "must not be blank")
    private String phone;

    @Min(value = 0, message = "must be positive")
    private Integer age;

    @NotNull(message = "cannot be null")
    @NotBlank(message = "must not be blank")
    private String taxpayer_number;

    @NotNull(message = "cannot be null")
    private LocalDate dob;

    @NotNull(message = "cannot be null")
    private Boolean married_status;

    @NotNull(message = "cannot be null")
    @NotBlank(message = "must not be blank")
    private String gender;

    @NotNull(message = "cannot be null")
    @Size(min = 1, message = "must not be empty")
    private List<Integer> checkbox;

    @NotNull(message = "cannot be null")
    @NotBlank(message = "must not be blank")
    private String input_date_year;

    @NotNull(message = "cannot be null")
    private LocalTime input_time;

    @NotNull(message = "cannot be null")
    @NotBlank(message = "must not be blank")
    private String address;

    private MediaDataResult profile_picture;

    private List<MediaDataResult> multiple_image;

    private MediaDataResult supporting_document;

    private List<MediaDataResult> multiple_file;
}
