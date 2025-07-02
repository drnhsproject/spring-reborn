package id.co.xinix.spring.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
@Getter
public class ApplicationProperties {

    private final Liquibase liquibase = new Liquibase();

    @Setter
    @Getter
    public static class Liquibase {
        private Boolean asyncStart;
    }
}
