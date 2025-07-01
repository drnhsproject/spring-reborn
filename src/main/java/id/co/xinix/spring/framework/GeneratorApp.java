package id.co.xinix.spring.framework;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.xinix.spring.services.EntityGeneratorService;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class GeneratorApp {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Please provide path to schema file (e.g., User.json)");
            return;
        }

        String schemaFile = args[0];

        String context = args.length >= 2 ? args[1] : "spring";

        Map<String, Object> config = loadGeneratorConfig();

        String schemaPath = (String) config.get("schemaPath");
        @SuppressWarnings("unchecked")
        Map<String, Object> contextPaths = (Map<String, Object>) config.get("contexts");

        String outputBaseDir = (String) contextPaths.getOrDefault(context, contextPaths.get("spring"));
        @SuppressWarnings("unchecked")
        Map<String, Object> liquibase = (Map<String, Object>) config.get("liquibase");
        String changelogDir = (String) liquibase.get("changelogDir");
        String masterFile = (String) liquibase.get("masterFile");

        String inputFile = schemaFile.startsWith("/") ? schemaFile : schemaPath + schemaFile;

        File jsonFile = new File(inputFile);
        if (!jsonFile.exists()) {
            System.out.println("JSON file not found: " + jsonFile.getAbsolutePath());
            return;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Entity entitySchema = objectMapper.readValue(jsonFile, Entity.class);

        EntityGeneratorService generatorService = new EntityGeneratorService(
                outputBaseDir, changelogDir, masterFile, context
        );

        generatorService.generate(entitySchema);
    }

    private static Map<String, Object> loadGeneratorConfig() throws IOException {
        Yaml yaml = new Yaml();
        try (InputStream in = new FileInputStream("src/main/resources/config/application-dev.yml")) {
            Map<String, Object> yamlData = yaml.load(in);
            return (Map<String, Object>) yamlData.get("generator");
        }
    }
}
