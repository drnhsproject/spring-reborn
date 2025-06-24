package id.co.xinix.spring.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration("coreJpaConfiguration")
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
@EntityScan(basePackages = {
        "id.co.xinix.auth",
        "id.co.xinix.media",
        "id.co.xinix.spring"
})
@EnableJpaRepositories(basePackages = {
        "id.co.xinix.auth.modules",
        "id.co.xinix.spring.modules",
        "id.co.xinix.media.modules"
})
public class JpaConfiguration {
}
