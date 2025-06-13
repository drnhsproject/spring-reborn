package id.co.xinix.spring.security.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;

@Component("coreJwtProperties")
@ConfigurationProperties(prefix = "spring.security.authentication.jwt")
@Getter
@Setter
@NoArgsConstructor
public class JwtProperties {

    private int tokenValidityInSeconds;

    private int tokenValidityInSecondsForRememberMe;

    private String base64Secret;

    private String contentSecurityPolicy = "default-src 'self'; frame-src 'self' data:; script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com; style-src 'self' 'unsafe-inline'; img-src 'self' data:; font-src 'self' data:";

    private CorsConfiguration cors;
}
