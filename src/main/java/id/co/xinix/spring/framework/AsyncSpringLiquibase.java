package id.co.xinix.spring.framework;

import liquibase.exception.LiquibaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.liquibase.DataSourceClosingSpringLiquibase;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.util.StopWatch;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Executor;

public class AsyncSpringLiquibase extends DataSourceClosingSpringLiquibase {
    public static final String DISABLED_MESSAGE = "Liquibase is disabled";
    public static final String STARTING_ASYNC_MESSAGE = "Starting Liquibase asynchronously, your database might not be ready at startup!";
    public static final String STARTING_SYNC_MESSAGE = "Starting Liquibase synchronously";
    public static final String STARTED_MESSAGE = "Liquibase has updated your database in {} ms";
    public static final String EXCEPTION_MESSAGE = "Liquibase could not start correctly, your database is NOT ready: {}";
    public static final long SLOWNESS_THRESHOLD = 5L;
    public static final String SLOWNESS_MESSAGE = "Warning, Liquibase took more than {} seconds to start up!";
    private final Logger logger = LoggerFactory.getLogger(AsyncSpringLiquibase.class);
    private final Executor executor;
    private final Environment env;

    public AsyncSpringLiquibase(Executor executor, Environment env) {
        this.executor = executor;
        this.env = env;
    }

    public void afterPropertiesSet() throws LiquibaseException {
        if (this.isLiquibaseDisabled()) {
            this.logger.debug("Liquibase is disabled");
        } else {
            if (this.isAsyncProfileActive()) {
                this.handleAsyncExecution();
            } else {
                this.logger.debug("Starting Liquibase synchronously");
                this.initDb();
            }

        }
    }

    private boolean isLiquibaseDisabled() {
        return this.env.acceptsProfiles(Profiles.of(new String[]{"no-liquibase"}));
    }

    private boolean isAsyncProfileActive() {
        return this.env.acceptsProfiles(Profiles.of(new String[]{"dev|heroku"}));
    }

    private void handleAsyncExecution() {
        try (Connection connection = this.getDataSource().getConnection()) {
            this.executor.execute(() -> {
                try {
                    this.logger.warn("Starting Liquibase asynchronously, your database might not be ready at startup!");
                    this.initDb();
                } catch (LiquibaseException e) {
                    this.logger.error("Liquibase could not start correctly, your database is NOT ready: {}", e.getMessage(), e);
                }

            });
        } catch (SQLException e) {
            this.logger.error("Liquibase could not start correctly, your database is NOT ready: {}", e.getMessage(), e);
        }

    }

    protected void initDb() throws LiquibaseException {
        StopWatch watch = new StopWatch();
        watch.start();
        super.afterPropertiesSet();
        watch.stop();
        this.logger.debug("Liquibase has updated your database in {} ms", watch.getTotalTimeMillis());
        boolean isExecutionTimeLong = watch.getTotalTimeMillis() > 5000L;
        if (isExecutionTimeLong) {
            this.logger.warn("Warning, Liquibase took more than {} seconds to start up!", 5L);
        }

    }
}