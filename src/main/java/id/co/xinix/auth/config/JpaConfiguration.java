package id.co.xinix.auth.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration("authJpaConfiguration")
@ConditionalOnMissingBean(name = "jpaAuditingHandler")
@EnableJpaAuditing(auditorAwareRef = "authAuditorAware")
public class JpaConfiguration {
}
