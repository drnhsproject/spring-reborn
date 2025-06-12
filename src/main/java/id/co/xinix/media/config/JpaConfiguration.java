package id.co.xinix.media.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration("mediaJpaConfiguration")
@ConditionalOnMissingBean(name = "jpaAuditingHandler")
@EnableJpaAuditing(auditorAwareRef = "mediaAuditorAware")
public class JpaConfiguration {
}
