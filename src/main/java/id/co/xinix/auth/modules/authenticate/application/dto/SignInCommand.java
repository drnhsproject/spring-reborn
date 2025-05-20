package id.co.xinix.auth.modules.authenticate.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignInCommand {

    @NotNull
    private String username;

    @NotNull
    private String password;

    private Boolean rememberMe = false;
}
