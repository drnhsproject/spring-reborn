package ${modulePackage}.application.dto;

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

public record ${entity.name}UpdatedResult(
<#list entity.fields as field>
    ${field.type} ${field.name}<#if field_has_next>,</#if>
</#list>
) {
}