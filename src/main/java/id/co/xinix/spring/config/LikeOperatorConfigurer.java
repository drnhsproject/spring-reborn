package id.co.xinix.spring.config;

import id.co.xinix.spring.services.LikeOperatorResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class LikeOperatorConfigurer {
    @Bean
    public LikeOperatorResolver likeOperator(DataSource dataSource) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            String dbName = conn.getMetaData().getDatabaseProductName();
            return new LikeOperatorResolver(dbName);
        }
    }
}
