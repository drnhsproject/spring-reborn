package id.co.xinix.auth.modules.authenticate.domain;

public record RoleSignIn(
    Long id,
    String code,
    String name,
    Integer status
) {
}
