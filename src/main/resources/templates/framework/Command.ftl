package ${modulePackage}.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

<#list entity.fields as field>
    <#if field.type == "BigDecimal">
import java.math.BigDecimal;
        <#break>
    </#if>
    <#if field.type == "Instant">
import java.time.Instant;
        <#break>
    </#if>
    <#if field.type == "LocalDate">
import java.time.LocalDate;
         <#break>
    </#if>
</#list>

@Getter
@Setter
@NoArgsConstructor
public class ${entity.name}Command {

<#list entity.fields as field>
    private ${field.type} ${field.name};
</#list>

}
