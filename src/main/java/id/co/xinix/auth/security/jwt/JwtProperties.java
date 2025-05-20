package id.co.xinix.auth.security.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.security.authentication.jwt")
@Getter
@Setter
@NoArgsConstructor
public class JwtProperties {

    private int tokenValidityInSeconds;

    private int tokenValidityInSecondsForRememberMe;

    private String base64Secret;

    private String contentSecurityPolicy = "default-src 'self'; frame-src 'self' data:; script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com; style-src 'self' 'unsafe-inline'; img-src 'self' data:; font-src 'self' data:";
}
