package ${modulePackage}.application.usecase;

import ${basePackage}.UseCase;
import ${basePackage}.exception.NotFoundException;
import ${modulePackage}.application.dto.${entity.name}Command;
import ${modulePackage}.application.dto.${entity.name}UpdatedResult;
import ${modulePackage}.domain.${entity.name};
import ${modulePackage}.domain.${entity.name}Repository;
import lombok.AllArgsConstructor;

@UseCase
@AllArgsConstructor
public class Change${entity.name}Detail {

    private final ${entity.name}Repository ${entityCamelCase}Repository;

    public ${entity.name}UpdatedResult handle(${entity.name}Command command) {
        ${entity.name} ${entityCamelCase} = get${entity.name}ById(command.getId());
        updated${entity.name}FromCommand(${entityCamelCase}, command);
        ${entity.name} updated = ${entityCamelCase}Repository.save(${entityCamelCase});
        return mapToUpdatedResult(updated);
    }

    private ${entity.name} get${entity.name}ById(Long id) {
        return ${entityCamelCase}Repository.findById(id)
            .orElseThrow(() -> new NotFoundException("${entitySpacedLower} not found"));
    }

    private void updated${entity.name}FromCommand(${entity.name} entity, ${entity.name}Command command) {
<#list entity.fields as field>
<#if field.name != "id">
        entity.set${field.name?cap_first}(command.get${field.name?cap_first}());
</#if>
</#list>
    }

    private ${entity.name}UpdatedResult mapToUpdatedResult(${entity.name} entity) {
        return new ${entity.name}UpdatedResult(
<#list entity.fields as field>
            entity.get${field.name?cap_first}()<#if field_has_next>,</#if>
</#list>
        );
    }
}