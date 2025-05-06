package id.co.xinix.spring.framework;

import java.util.HashMap;
import java.util.Map;

public class SqlTypeMapper {

    private static final Map<String, Map <String, String>> TYPE_MAPPING = new HashMap<>();

    static {
        Map<String, String> postgres = new HashMap<>();
        postgres.put("String", "varchar(255)");
        postgres.put("Long", "bigint");
        postgres.put("Integer", "integer");
        postgres.put("LocalDate", "date");

        Map<String, String> mysql = new HashMap<>();
        mysql.put("String", "varchar(255)");
        mysql.put("Long", "bigint");
        mysql.put("Integer", "int");
        mysql.put("LocalDate", "date");

        TYPE_MAPPING.put("postgresql", postgres);
        TYPE_MAPPING.put("mysql", mysql);
    }

    public static String map(String javaType, String dbType) {
        return TYPE_MAPPING.getOrDefault(dbType, new HashMap<>()).getOrDefault(javaType, "varchar(255)");
    }
}
