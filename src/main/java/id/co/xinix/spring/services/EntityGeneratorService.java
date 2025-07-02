package id.co.xinix.spring.services;

import freemarker.template.TemplateException;
import id.co.xinix.spring.Application;
import id.co.xinix.spring.framework.Entity;
import freemarker.template.Configuration;
import freemarker.template.Template;
import id.co.xinix.spring.framework.Field;
import id.co.xinix.spring.framework.SqlTypeMapper;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EntityGeneratorService {

    private final String outputBaseDir;
    private final String changelogDir;
    private final String masterFile;
    private final String context;
    private final String dbType;

    private final Configuration cfg;
    private final FormatterString formatterString;

    public EntityGeneratorService(String outputBaseDir, String changelogDir, String masterFile, String context, String dbType) {
        this.outputBaseDir = outputBaseDir;
        this.changelogDir = changelogDir;
        this.masterFile = masterFile;
        this.context = context;
        this.dbType = dbType;
        cfg = new Configuration(Configuration.VERSION_2_3_32);
        cfg.setClassLoaderForTemplateLoading(
                Thread.currentThread().getContextClassLoader(), "/templates/framework"
        );

        formatterString = new FormatterString();
    }

    public void generate(Entity entitySchema) throws Exception {
        String timestamp = new GenerateTimestamp().generate();

        // Load templates
        Template entityTemplate = cfg.getTemplate("Entity.ftl");
        Template repositoryTemplate = cfg.getTemplate("Repository.ftl");

        Template dtoTemplate = cfg.getTemplate("Dto.ftl");
        Template commandTemplate = cfg.getTemplate("Command.ftl");
        Template queryFilterTemplate = cfg.getTemplate("QueryFilter.ftl");
        Template pagedResultTemplate = cfg.getTemplate("PagedResult.ftl");
        Template createdResultTemplate = cfg.getTemplate("Created-result.ftl");
        Template updatedResultTemplate = cfg.getTemplate("UpdatedResult.ftl");
        Template detailResultTemplate = cfg.getTemplate("DetailResult.ftl");
        Template useCaseCreateTemplate = cfg.getTemplate("UseCase-create.ftl");
        Template useCaseChangeDetailTemplate = cfg.getTemplate("ChangeDetail.ftl");
        Template useCaseGetDetailTemplate = cfg.getTemplate("GetDetailById.ftl");
        Template useCaseArchiveTemplate = cfg.getTemplate("Archive.ftl");
        Template useCaseRemoveTemplate = cfg.getTemplate("Remove.ftl");
        Template useCaseRestoreTemplate = cfg.getTemplate("Restore.ftl");
        Template baseQueryResultTemplate = cfg.getTemplate("BaseQueryResult.ftl");
        Template baseQueryGetListTemplate = cfg.getTemplate("BaseQueryGetList.ftl");
        Template getListTemplate = cfg.getTemplate("GetList.ftl");

        Template changelogTemplate = cfg.getTemplate("Liquibase-changelog.ftl");
        Template resourceTemplate = cfg.getTemplate("Resource.ftl");

        String entityNameLower = entitySchema.getName().toLowerCase();
        String basePackage = Application.class.getPackage().getName();
        String boundedBasePackage = basePackage.replaceFirst("\\bspring\\b", context);
        String modulePackage = boundedBasePackage + ".modules." + entityNameLower;

        LikeOperatorResolver likeOperatorResolver= new LikeOperatorResolver(dbType);
        String like = likeOperatorResolver.get();

        String searchableFields = entitySchema.getFields().stream()
                .filter(field -> Boolean.TRUE.equals(field.getSearchable()))
                .filter(field -> field.getType().equals("String"))
                .map(field -> formatterString.toSnakeCase(field.getName()) + " " + like + " :search")
                .collect(Collectors.joining(" OR "));

        List<String> columnMappings = entitySchema.getFields().stream()
                .map(field -> {
                    String snakeCase = formatterString.toSnakeCase(field.getName());
                    String camelCase = field.getName();
                    return snakeCase.equals(camelCase)
                            ? " " + snakeCase
                            : " " + snakeCase + " AS \"" + camelCase + "\"";
                })
                .collect(Collectors.toList());

        String fieldColumns = String.join(",", columnMappings);

        Map<String, Object> data = new HashMap<>();
        data.put("entity", entitySchema);
        data.put("basePackage", basePackage);
        data.put("modulePackage", modulePackage);
        data.put("boundedBasePackage", boundedBasePackage);
        data.put("changelogId", timestamp + "-1");
        data.put("searchableFields", searchableFields);
        data.put("entityKebabCase", formatterString.toKebabCase(entitySchema.getName()));
        data.put("entityCamelCase", formatterString.toCamelCase(entitySchema.getName()));
        data.put("entitySpacedLower", formatterString.toSpacedLowerCase(entitySchema.getName()));
        data.put("entitySpaced", formatterString.toSpaced(entitySchema.getName()));
        data.put("fieldColumns", fieldColumns);

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

        // Generate Created Result
        String createdResultOutputPath = baseDir + "application/dto/" + entitySchema.getName() + "CreatedResult.java";
        generateFile(createdResultTemplate, data, createdResultOutputPath);

        // Generate Updated Result
        String updatedResultOutputPath = baseDir + "application/dto/" + entitySchema.getName() + "UpdatedResult.java";
        generateFile(updatedResultTemplate, data, updatedResultOutputPath);

        // Generate Updated Result
        String detailResultOutputPath = baseDir + "application/dto/" + entitySchema.getName() + "DetailResult.java";
        generateFile(detailResultTemplate, data, detailResultOutputPath);

        // Generate Command
        String commandOutputPath = baseDir + "application/dto/" + entitySchema.getName() + "Command.java";
        generateFile(commandTemplate, data, commandOutputPath);

        // Generate QueryFilter
        String queryFilterOutputPath = baseDir + "application/dto/QueryFilter.java";
        generateFile(queryFilterTemplate, data, queryFilterOutputPath);

        // Generate PagedResult
        String pagedResultOutputPath = baseDir + "application/dto/PagedResult.java";
        generateFile(pagedResultTemplate, data, pagedResultOutputPath);

        // Generate BaseQueryResult
        String baseQueryResultOutputPath = baseDir + "application/dto/BaseQueryResult.java";
        generateFile(baseQueryResultTemplate, data, baseQueryResultOutputPath);

        // Generate UseCase
        String useCasePath = "application/usecase/";
        String useCaseCreateOutputPath = baseDir + useCasePath + "Create" + entitySchema.getName() + ".java";
        generateFile(useCaseCreateTemplate, data, useCaseCreateOutputPath);

        String useCaseGetListOutputPath = baseDir + useCasePath + "Get" + entitySchema.getName() + "List.java";
        generateFile(getListTemplate, data, useCaseGetListOutputPath);

        String useCaseChangeDetailOutputPath = baseDir + useCasePath + "Change" + entitySchema.getName() + "Detail.java";
        generateFile(useCaseChangeDetailTemplate, data, useCaseChangeDetailOutputPath);

        String useCaseGetDetailDetailOutputPath = baseDir + useCasePath + "Get" + entitySchema.getName() + "DetailById.java";
        generateFile(useCaseGetDetailTemplate, data, useCaseGetDetailDetailOutputPath);

        String useCaseArchiveOutputPath = baseDir + useCasePath + "Archive" + entitySchema.getName() + ".java";
        generateFile(useCaseArchiveTemplate, data, useCaseArchiveOutputPath);

        String useCaseRemoveOutputPath = baseDir + useCasePath + "Remove" + entitySchema.getName() + ".java";
        generateFile(useCaseRemoveTemplate, data, useCaseRemoveOutputPath);

        String useCaseRestoreOutputPath = baseDir + useCasePath + "Restore" + entitySchema.getName() + ".java";
        generateFile(useCaseRestoreTemplate, data, useCaseRestoreOutputPath);

        // Generate Resource
        String resourceOutputPath = baseDir + "infrastructure/rest/" + entitySchema.getName() + "Resource.java";
        generateFile(resourceTemplate, data, resourceOutputPath);

        // Generate BaseQueryGetList
        String baseQueryGetListOutputPath = baseDir + "infrastructure/persistence/BaseQueryGetList.java";
        generateFile(baseQueryGetListTemplate, data, baseQueryGetListOutputPath);

        for (Field field : entitySchema.getFields()) {
            String snakeCaseName = formatterString.toSnakeCase(field.getName());
            field.setName(snakeCaseName);

            String sqlType = SqlTypeMapper.map(field.getType(), dbType);
            field.setSqlType(sqlType);

            if ("id".equalsIgnoreCase(snakeCaseName) && "Long".equals(field.getType())) {
                field.setPrimaryKey(true);
                field.setAutoIncrement(true);
            }
        }

        // Generate Changelog
        String changelogName = timestamp + "_added_entity_" + entitySchema.getName() + ".xml";
        String changelogOutputPath = changelogDir + changelogName;
        generateFile(changelogTemplate, data, changelogOutputPath);
        appendChangelogToMaster(changelogName);
    }

    private String getBaseDirModule(String entityNameLower) {
        String baseDir = outputBaseDir + entityNameLower + "/";

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

    private void appendChangelogToMaster(String changelogFilename) throws IOException {
        String masterPath = masterFile;
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

