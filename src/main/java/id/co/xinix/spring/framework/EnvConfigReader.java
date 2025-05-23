package id.co.xinix.spring.framework;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnvConfigReader {
    public static String getDatasourceUrl() {
        try {
            Map<String, String> envVars = loadDotEnv(".env");
            String url = envVars.get("DATASOURCE_URL");
            if (url == null || url.isEmpty()) {
                throw new RuntimeException("DATASOURCE_URL not found in .env");
            }
            return url;
        } catch (IOException e) {
            throw new RuntimeException("Failed to read .env file", e);
        }
    }

    private static Map<String, String> loadDotEnv(String path) throws IOException {
        Map<String, String> env = new HashMap<>();
        List<String> lines = Files.readAllLines(Paths.get(path));
        for (String line : lines) {
            if (!line.trim().startsWith("#") && line.contains("=")) {
                String[] parts = line.split("=", 2);
                env.put(parts[0].trim(), parts[1].trim());
            }
        }
        return env;
    }
}
