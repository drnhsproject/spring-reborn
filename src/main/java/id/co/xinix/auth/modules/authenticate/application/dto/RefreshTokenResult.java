package id.co.xinix.auth.modules.authenticate.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RefreshTokenResult {
    private final String accessToken;

    public RefreshTokenResult(String refreshToken) {
        this.accessToken = refreshToken;
    }

    @JsonProperty("access_token")
    String getAccessToken() {
        return accessToken;
    }
}
