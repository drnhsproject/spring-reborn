package id.co.xinix.spring.modules.userinformation.application.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserInformationDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String first_name;

    private String last_name;

    private String gender;

    @NotNull
    private String phone_number;

    @NotNull
    private LocalDate birth_date;

    private Integer age;

}