package id.co.xinix.auth.modules.user.application.dto;

public record UserResult(
        Long id,
        String firstName,
        String lastName,
        String username,
        String email,
        Integer status
) {
}
