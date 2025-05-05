package id.co.xinix.spring.services;

import id.co.xinix.spring.Application;
import id.co.xinix.spring.framework.Entity;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@Service
public class EntityGeneratorService {

    private final Configuration cfg;

    public EntityGeneratorService() {
        cfg = new Configuration(Configuration.VERSION_2_3_32);
        cfg.setClassLoaderForTemplateLoading(
                Thread.currentThread().getContextClassLoader(), "/templates"
        );
    }

    public void generate(Entity entitySchema) throws Exception {
        // Load templates
        Template entityTemplate = cfg.getTemplate("Entity.ftl");
        Template repositoryTemplate = cfg.getTemplate("Repository.ftl");
        Template dtoTemplate = cfg.getTemplate("Dto.ftl");

        String entityNameLower = entitySchema.getName().toLowerCase();
        String basePackage = Application.class.getPackage().getName();
        String modulePackage = basePackage + ".modules." + entityNameLower;

        Map<String, Object> data = new HashMap<>();
        data.put("entity", entitySchema);
        data.put("basePackage", basePackage);
        data.put("modulePackage", modulePackage);

        String baseDir = getBaseDirModule(entityNameLower);

        // Generate Entity
        String entityOutputPath = baseDir + "domain/" + entitySchema.getName() + ".java";
        try (Writer writer = new FileWriter(entityOutputPath)) {
            entityTemplate.process(data, writer);
            System.out.println("Entity generated: " + entityOutputPath);
        }

        // Generate Repository
        String repositoryOutputPath = baseDir + "domain/" + entitySchema.getName() + "Repository.java";
        try (Writer writer = new FileWriter(repositoryOutputPath)) {
            repositoryTemplate.process(data, writer);
            System.out.println("Repository generated: " + repositoryOutputPath);
        }

        // Generate DTO
        String dtoOutputPath = baseDir + "application/dto/" + entitySchema.getName() + "DTO.java";
        try (Writer writer = new FileWriter(dtoOutputPath)) {
            dtoTemplate.process(data, writer);
            System.out.println("DTO generated: " + dtoOutputPath);
        }
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
}

