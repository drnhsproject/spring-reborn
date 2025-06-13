package id.co.xinix.auth.modules.user.application.dto;

public record UserUpdatedResult(
    Long id,
    String username,
    String email
) {}
