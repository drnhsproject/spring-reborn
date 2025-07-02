package ${modulePackage}.application.dto;

<#assign needBigDecimal = false>
<#assign needTimestamp = false>
<#assign needSqlDate = false>
<#list entity.fields as field>
    <#if field.type == "BigDecimal">
        <#assign needBigDecimal = true>
    </#if>
    <#if field.type == "Instant">
        <#assign needTimestamp = true>
    </#if>
    <#if field.type == "LocalDate">
        <#assign needSqlDate = true>
    </#if>
</#list>
<#if needBigDecimal>
import java.math.BigDecimal;
</#if>
<#if needTimestamp>
import java.sql.Timestamp;
</#if>
<#if needSqlDate>
import java.sql.Date;
</#if>

public record PagedResult(
<#list entity.fields as field>
<#if field.type == "BigDecimal">
    BigDecimal ${field.name}<#if field_has_next>,</#if>
<#elseif field.type == "Instant">
    Timestamp ${field.name}<#if field_has_next>,</#if>
<#elseif field.type == "LocalDate">
    Date ${field.name}<#if field_has_next>,</#if>
<#else>
    ${field.type} ${field.name}<#if field_has_next>,</#if>
</#if>
</#list>
) {
}