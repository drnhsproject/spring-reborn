package id.co.xinix.auth.modules.user.application.dto;

public record UserDetailResult(
    Long id,
    String username,
    String email
) {}
