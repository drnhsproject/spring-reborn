package id.co.xinix.spring.framework;

import java.util.HashMap;
import java.util.Map;

public class SqlTypeMapper {

    private static final Map<String, Map <String, String>> TYPE_MAPPING = new HashMap<>();

    static {
        Map<String, String> mysql = new HashMap<>();
        mysql.put("String", "varchar(255)");
        mysql.put("Integer", "int");
        mysql.put("Long", "bigint");
        mysql.put("Float", "float");
        mysql.put("Double", "double");
        mysql.put("Boolean", "tinyint(1)");
        mysql.put("BigDecimal", "decimal");
        mysql.put("BigInteger", "decimal");
        mysql.put("Date", "date");
        mysql.put("Time", "time");
        mysql.put("Timestamp", "timestamp");
        mysql.put("LocalDate", "date");
        mysql.put("LocalDateTime", "timestamp");

        Map<String, String> postgres = new HashMap<>();
        postgres.put("String", "varchar(255)");
        postgres.put("Integer", "integer");
        postgres.put("Long", "bigint");
        postgres.put("Float", "real");
        postgres.put("Double", "double");
        postgres.put("Boolean", "boolean");
        postgres.put("BigDecimal", "numeric");
        postgres.put("BigInteger", "numeric");
        postgres.put("Date", "date");
        postgres.put("Time", "time");
        postgres.put("Timestamp", "timestamp");
        postgres.put("LocalDate", "date");
        postgres.put("LocalDateTime", "datetime");

        TYPE_MAPPING.put("mysql", mysql);
        TYPE_MAPPING.put("postgresql", postgres);
    }

    public static String map(String javaType, String dbType) {
        return TYPE_MAPPING.getOrDefault(dbType, new HashMap<>()).getOrDefault(javaType, "varchar(255)");
    }
}
