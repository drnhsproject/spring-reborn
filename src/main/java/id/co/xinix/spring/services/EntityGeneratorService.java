package id.co.xinix.spring.services;

import freemarker.template.TemplateException;
import id.co.xinix.spring.Application;
import id.co.xinix.spring.framework.Entity;
import freemarker.template.Configuration;
import freemarker.template.Template;
import id.co.xinix.spring.framework.Field;
import id.co.xinix.spring.framework.SqlTypeMapper;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EntityGeneratorService {

    private final Configuration cfg;

    public EntityGeneratorService() {
        cfg = new Configuration(Configuration.VERSION_2_3_32);
        cfg.setClassLoaderForTemplateLoading(
                Thread.currentThread().getContextClassLoader(), "/templates/framework"
        );
    }

    public void generate(Entity entitySchema) throws Exception {
        String timestamp = new GenerateTimestamp().generate();

        for (Field field : entitySchema.getFields()) {
            String snakeCaseName = toSnakeCase(field.getName());
            field.setName(snakeCaseName);

            String sqlType = SqlTypeMapper.map(field.getType(), "mysql");
            field.setSqlType(sqlType);

            if ("id".equalsIgnoreCase(snakeCaseName) && "Long".equals(field.getType())) {
                field.setPrimaryKey(true);
                field.setAutoIncrement(true);
            }
        }

        // Load templates
        Template entityTemplate = cfg.getTemplate("Entity.ftl");
        Template repositoryTemplate = cfg.getTemplate("Repository.ftl");
        Template dtoTemplate = cfg.getTemplate("Dto.ftl");
        Template changelogTemplate = cfg.getTemplate("Liquibase-changelog.ftl");

        String entityNameLower = entitySchema.getName().toLowerCase();
        String basePackage = Application.class.getPackage().getName();
        String modulePackage = basePackage + ".modules." + entityNameLower;

        Map<String, Object> data = new HashMap<>();
        data.put("entity", entitySchema);
        data.put("basePackage", basePackage);
        data.put("modulePackage", modulePackage);
        data.put("changelogId", timestamp + "-1");

        String baseDir = getBaseDirModule(entityNameLower);

        // Ensure directories exist
        File entityDir = new File(baseDir + "domain");
        entityDir.mkdirs();

        // Generate Entity
        String entityOutputPath = baseDir + "domain/" + entitySchema.getName() + ".java";
        generateFile(entityTemplate, data, entityOutputPath);

        // Generate Repository
        String repositoryOutputPath = baseDir + "domain/" + entitySchema.getName() + "Repository.java";
        generateFile(repositoryTemplate, data, repositoryOutputPath);

        // Generate DTO
        String dtoOutputPath = baseDir + "application/dto/" + entitySchema.getName() + "DTO.java";
        generateFile(dtoTemplate, data, dtoOutputPath);

        // Generate Changelog
        String changelogName = timestamp + "_added_entity_" + entitySchema.getName() + ".xml";
        String changelogOutputPath = "src/main/resources/config/liquibase/changelog/" + changelogName;
        generateFile(changelogTemplate, data, changelogOutputPath);
        appendChangelogToMaster(changelogName);
    }

    private String getBaseDirModule(String entityNameLower) {
        String baseDir = "src/main/java/id/co/xinix/spring/modules/" + entityNameLower + "/";

        // structure folder
        String[] subFolders = {
                "application/dto",
                "application/usecase",
                "domain",
                "infrastructure/persistence",
                "infrastructure/rest"
        };

        for (String folder : subFolders) {
            File subDir = new File(baseDir + folder);
            if (!subDir.exists()) {
                boolean created = subDir.mkdirs();
                if (created) {
                    System.out.println("Directory created: " + subDir.getPath());
                } else {
                    System.err.println("Failed to create directory: " + subDir.getPath());
                }
            }
        }

        return baseDir;
    }

    private void generateFile(Template template, Map<String, Object> data, String outputPath) {
        try (Writer writer = new FileWriter(outputPath)) {
            template.process(data, writer);
            System.out.println(outputPath + " generated.");
        } catch (IOException | TemplateException e) {
            System.err.println("Error generating " + outputPath);
            e.printStackTrace();
        }
    }

    private String toSnakeCase(String input) {
        if (input == null) {
            return null;
        }

        return input.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }

    private void appendChangelogToMaster(String changelogFilename) throws IOException {
        String masterPath = "src/main/resources/config/liquibase/master.xml";
        File masterFile = new File(masterPath);

        List<String> lines = Files.readAllLines(masterFile.toPath());
        int insertIndex = -1;

        for (int i = lines.size() - 1; i >= 0; i--) {
            if (lines.get(i).trim().equals("</databaseChangeLog>")) {
                insertIndex = i;
                break;
            }
        }

        if (insertIndex != -1) {
            String includeLine = String.format("    <include file=\"config/liquibase/changelog/%s\" relativeToChangelogFile=\"false\"/>", changelogFilename);
            lines.add(insertIndex, includeLine);
            Files.write(masterFile.toPath(), lines);
            System.out.println("Changelog registered to master.xml: " + changelogFilename);
        } else {
            System.err.println("Could not find </databaseChangeLog> in master.xml");
        }
    }
}

