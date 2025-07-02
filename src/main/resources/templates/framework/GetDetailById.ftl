package ${modulePackage}.application.usecase;

import ${basePackage}.UseCase;
import ${basePackage}.exception.NotFoundException;
import ${modulePackage}.application.dto.${entity.name}DetailResult;
import ${modulePackage}.domain.${entity.name};
import ${modulePackage}.domain.${entity.name}Repository;
import lombok.AllArgsConstructor;

@UseCase
@AllArgsConstructor
public class Get${entity.name}DetailById {
    private final ${entity.name}Repository ${entityCamelCase}Repository;

    public ${entity.name}DetailResult handle(Long id) {
        ${entity.name} ${entityCamelCase} = ${entityCamelCase}Repository.findById(id)
            .orElseThrow(() -> new NotFoundException("${entitySpacedLower} not found"));

        return mapToDto(${entityCamelCase});
    }

    private ${entity.name}DetailResult mapToDto(${entity.name} ${entityCamelCase}) {
        return new ${entity.name}DetailResult(
            <#list entity.fields as field>
                ${entityCamelCase}.get${field.name?cap_first}()<#if field_has_next>,</#if>
            </#list>
        );
    }
}