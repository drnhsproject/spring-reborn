package id.co.xinix.spring.config;

import id.co.xinix.spring.services.SqlQuoter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class SqlQuoterConfig {
    @Bean
    public SqlQuoter sqlQuoter(DataSource dataSource) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            String dbProductName = conn.getMetaData().getDatabaseProductName();
            return new SqlQuoter(dbProductName);
        }
    }
}
