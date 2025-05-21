package id.co.xinix.auth.modules.authenticate.application.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenCommand(@NotBlank String access_token) {
}
