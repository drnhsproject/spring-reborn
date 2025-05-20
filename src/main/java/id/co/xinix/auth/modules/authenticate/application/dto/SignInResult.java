package id.co.xinix.auth.modules.authenticate.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import id.co.xinix.auth.modules.authenticate.domain.RolePrivilegeDetail;
import id.co.xinix.auth.modules.authenticate.domain.UserDetail;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignInResult {

    private UserDetail user;

    private RolePrivilegeDetail role;

    @JsonProperty("access_token")
    private String accessToken;
}
