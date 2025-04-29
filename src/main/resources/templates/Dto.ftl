package ${basePackage}.application.dto;

<#list entity.fields as field>
<#if field.type == "BigDecimal">
import java.math.BigDecimal;
<#if field.type == "Instant">
import java.time.Instant;
<#break>
</#if>
</#if>
</#list>

import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ${entity.name}DTO implements Serializable {

<#list entity.fields as field>
    <#-- Validations -->
    <#if field.validations??>
    <#if field.validations.required?? && field.validations.required>
    @NotNull
    </#if>
    <#if field.validations.minLength?? && field.validations.maxLength??>
    @Size(min = ${field.validations.minLength}, max = ${field.validations.maxLength})
    <#elseif field.validations.minLength??>
    @Size(min = ${field.validations.minLength})
    <#elseif field.validations.maxLength??>
    @Size(max = ${field.validations.maxLength})
    </#if>
    <#if field.validations.min??>
    @Min(value = ${field.validations.min})
    </#if>
    <#if field.validations.max??>
    @Max(value = ${field.validations.max})
    </#if>
    </#if>
    <#-- Field Declaration -->
    private ${field.type} ${field.name}<#if field.defaultValue??> = <#if field.type == "String">"${field.defaultValue}"<#elseif field.type == "BigDecimal">new BigDecimal("${field.defaultValue}")<#else>${field.defaultValue}</#if></#if>;

</#list>
}