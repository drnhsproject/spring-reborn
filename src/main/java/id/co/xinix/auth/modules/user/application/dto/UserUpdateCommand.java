package id.co.xinix.auth.modules.user.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateCommand {

    private Long id;

    @NotBlank(message = "must be not blank")
    @NotNull(message = "must be not null")
    private String email;

    @NotBlank(message = "must be not blank")
    @NotNull(message = "must be not null")
    private String username;

    private String password;

    private List<String> roles;
}
