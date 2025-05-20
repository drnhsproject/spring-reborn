package id.co.xinix.auth.modules.user.application.dto;

public record UserRegisteredResult(
        Long id,
        String email,
        String username
) {
}
