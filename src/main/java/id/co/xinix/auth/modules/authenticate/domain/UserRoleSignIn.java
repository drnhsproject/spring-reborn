package id.co.xinix.auth.modules.authenticate.domain;

import java.util.List;

public record UserRoleSignIn(
    Long id,
    Long user_id,
    RoleSignIn roles_id,
    Integer status
) {
}
