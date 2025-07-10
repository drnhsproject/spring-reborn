package id.co.xinix.auth.modules.authenticate.application.dto;

public record RefreshTokenCommand(
        String access_token
) {
}
