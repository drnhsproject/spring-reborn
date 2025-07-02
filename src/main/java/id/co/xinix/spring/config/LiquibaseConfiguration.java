package id.co.xinix.spring.config;

import id.co.xinix.spring.framework.ProfileConstants;
import id.co.xinix.spring.framework.SpringLiquibaseUtil;
import liquibase.integration.spring.SpringLiquibase;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;

import javax.sql.DataSource;
import java.util.concurrent.Executor;

@Configuration
public class LiquibaseConfiguration {

    private static final Logger log = LoggerFactory.getLogger(LiquibaseConfiguration.class);

    private final Environment env;

    @Value("${application.liquibase.async-start:true}")
    private Boolean asyncStart;

    public LiquibaseConfiguration(Environment env) {
        this.env = env;
    }

    @Bean
    public SpringLiquibase liquibase(
            @Qualifier("taskExecutor") Executor executor,
            LiquibaseProperties liquibaseProperties,
            @LiquibaseDataSource ObjectProvider<DataSource> liquibaseDataSource,
            ObjectProvider<DataSource> dataSource,
            DataSourceProperties dataSourceProperties
    ) {
        SpringLiquibase liquibase;
        if (Boolean.TRUE.equals(asyncStart)) {
            liquibase = SpringLiquibaseUtil.createAsyncSpringLiquibase(
                    this.env,
                    executor,
                    liquibaseDataSource.getIfAvailable(),
                    liquibaseProperties,
                    dataSource.getIfUnique(),
                    dataSourceProperties
            );
        } else {
            liquibase = SpringLiquibaseUtil.createSpringLiquibase(
                    liquibaseDataSource.getIfAvailable(),
                    liquibaseProperties,
                    dataSource.getIfUnique(),
                    dataSourceProperties
            );
        }
        liquibase.setChangeLog("classpath:config/liquibase/master.xml");
        liquibase.setContexts(String.valueOf(liquibaseProperties.getContexts()));
        liquibase.setDefaultSchema(liquibaseProperties.getDefaultSchema());
        liquibase.setLiquibaseSchema(liquibaseProperties.getLiquibaseSchema());
        liquibase.setLiquibaseTablespace(liquibaseProperties.getLiquibaseTablespace());
        liquibase.setDatabaseChangeLogLockTable(liquibaseProperties.getDatabaseChangeLogLockTable());
        liquibase.setDatabaseChangeLogTable(liquibaseProperties.getDatabaseChangeLogTable());
        liquibase.setDropFirst(liquibaseProperties.isDropFirst());
        liquibase.setLabelFilter(String.valueOf(liquibaseProperties.getLabelFilter()));
        liquibase.setChangeLogParameters(liquibaseProperties.getParameters());
        liquibase.setRollbackFile(liquibaseProperties.getRollbackFile());
        liquibase.setTestRollbackOnUpdate(liquibaseProperties.isTestRollbackOnUpdate());
        if (env.acceptsProfiles(Profiles.of(ProfileConstants.SPRING_PROFILE_NO_LIQUIBASE))) {
            liquibase.setShouldRun(false);
        } else {
            liquibase.setShouldRun(liquibaseProperties.isEnabled());
            log.debug("Configuring Liquibase");
        }
        return liquibase;
    }
}
