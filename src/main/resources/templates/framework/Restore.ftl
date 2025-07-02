package ${modulePackage}.application.usecase;

import ${basePackage}.UseCase;
import ${basePackage}.exception.NotFoundException;
import ${modulePackage}.domain.${entity.name};
import ${modulePackage}.domain.${entity.name}Repository;
import lombok.AllArgsConstructor;

@UseCase
@AllArgsConstructor
public class Restore${entity.name} {

    private final ${entity.name}Repository ${entityCamelCase}Repository;

    public void handle(Long id) {
        ${entity.name} ${entityCamelCase} = ${entityCamelCase}Repository.findById(id)
            .orElseThrow(() -> new NotFoundException("${entitySpacedLower} not found"));

        ${entityCamelCase}.setIsActive(true);
        ${entityCamelCase}.setStatus(1);

        ${entityCamelCase}Repository.save(${entityCamelCase});
    }
}