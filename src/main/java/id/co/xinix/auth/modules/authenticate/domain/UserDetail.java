package id.co.xinix.auth.modules.authenticate.domain;

import jakarta.annotation.Nullable;

import java.util.List;

public record UserDetail(
    Long id,
    String username,
    String email,
    Integer status,
    String first_name,
    String last_name,
    String photo,
    List<String> role,
    List<UserRoleSignIn> roles
) {
}
