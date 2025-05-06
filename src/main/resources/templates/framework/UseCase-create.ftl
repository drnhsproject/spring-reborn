package ${modulePackage}.application.usecase;

import ${basePackage}.UseCase;
import ${modulePackage}.application.dto.*;
import ${modulePackage}.domain.*;
import lombok.AllArgsConstructor;

@UseCase
@AllArgsConstructor
public class Create${entity.name} {

    private final ${entity.name}Repository ${entity.name?uncap_first}Repository;

    public ${entity.name}CreatedResult handle(${entity.name}Command command) {
        ${entity.name} ${entity.name?uncap_first} = new ${entity.name}();
<#list entity.fields as field>
        ${entity.name?uncap_first}.set${field.name?cap_first}(command.get${field.name?cap_first}());
</#list>

        ${entity.name} saved${entity.name} = ${entity.name?uncap_first}Repository.save(${entity.name?uncap_first});

        return new ${entity.name}CreatedResult(
<#list entity.fields as field>
            saved${entity.name}.get${field.name?cap_first}()<#if field_has_next>,</#if>
</#list>
        );
    }
}
