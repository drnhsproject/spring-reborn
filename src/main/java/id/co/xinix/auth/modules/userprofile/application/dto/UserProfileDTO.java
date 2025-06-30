package id.co.xinix.auth.modules.userprofile.application.dto;


import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserProfileDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    @NotNull
    @Size(max = 255)
    private String firstName;

    private String lastName;

    private String identityNumber;

    private String phoneNumber;

    @Size(max = 5000)
    private String photo;

    @Size(max = 5000)
    private String address;

    @NotNull
    private Long userId;

}
