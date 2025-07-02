package ${modulePackage}.application.usecase;

import ${basePackage}.UseCase;
import ${basePackage}.exception.NotFoundException;
import ${modulePackage}.domain.${entity.name}Repository;
import lombok.AllArgsConstructor;

@UseCase
@AllArgsConstructor
public class Remove${entity.name} {

    private final ${entity.name}Repository ${entityCamelCase}Repository;

    public void handle(Long id) {
        if (!${entityCamelCase}Repository.existsById(id)) {
            throw new NotFoundException("${entitySpacedLower} not found");
        }

        ${entityCamelCase}Repository.deleteById(id);
    }
}