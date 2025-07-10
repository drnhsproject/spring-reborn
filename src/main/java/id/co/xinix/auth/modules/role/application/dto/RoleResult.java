package id.co.xinix.auth.modules.role.application.dto;

public record RoleResult(
    Long id,
    String code,
    String name,
    Integer status
) {
}
