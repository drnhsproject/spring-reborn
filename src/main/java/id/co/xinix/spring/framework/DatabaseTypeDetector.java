package id.co.xinix.spring.framework;

import java.util.Map;

public class DatabaseTypeDetector {
    private static final Map<String, String> DB_IDENTIFIER_MAP = Map.of(
            "postgresql", "postgresql",
            "mysql", "mysql",
            "mariadb", "mysql",
            "oracle", "oracle",
            "sqlserver", "sqlserver"
    );

    public static String detectDatabaseType(String jdbcUrl) {
        for (Map.Entry<String, String> entry : DB_IDENTIFIER_MAP.entrySet()) {
            if (jdbcUrl.toLowerCase().contains(entry.getKey())) {
                return entry.getValue();
            }
        }
        throw new RuntimeException("Database tidak dikenali dari URL: " + jdbcUrl);
    }
}
