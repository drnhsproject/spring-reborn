package id.co.xinix.spring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
public class JpaConfiguration {
}
