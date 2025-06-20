package id.co.xinix.spring.framework;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.xinix.spring.services.EntityGeneratorService;

import java.io.File;

public class GeneratorApp {

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Please provide path to schema folder");
            return;
        }

        String inputFile = args[0];
        if (!inputFile.startsWith("schema/")) {
            inputFile = "schema/" + inputFile;
        }

        File jsonFile = new File(inputFile);

        if (!jsonFile.exists()) {
            System.out.println("JSON file not found: " + jsonFile.getAbsolutePath());
            return;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Entity entitySchema = objectMapper.readValue(jsonFile, Entity.class);

        EntityGeneratorService generatorService = new EntityGeneratorService();
        generatorService.generate(entitySchema);
    }
}
