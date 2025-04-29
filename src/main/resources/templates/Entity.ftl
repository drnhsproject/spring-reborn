package ${basePackage}.domain;

import id.co.xinix.spring.services.BaseColumnEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

<#-- Import type data -->
<#list entity.fields as field>
    <#if field.type == "BigDecimal">
import java.math.BigDecimal;
        <#break>
    </#if>
    <#if field.type == "Instant">
import java.time.Instant;
        <#break>
    </#if>
</#list>

import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "${entity.name?lower_case}")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ${entity.name} extends BaseColumnEntity {

<#list entity.fields as field>
    <#assign columnName = field.name?replace('([a-z])([A-Z])', '$1_$2', 'r')?lower_case>
    <#if field.primaryKey?? && field.primaryKey>
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "${columnName}")
    private ${field.type} ${field.name};
    <#else>
        <#-- Add NotNull Validation -->
        <#if field.validations?? && field.validations.required?? && field.validations.required>
    @NotNull
        </#if>
        <#-- Check if unique or nullable attributes are needed -->
        <#if field.validations?? && (field.validations.unique?? && field.validations.unique || field.validations.required?? && field.validations.required)>
    @Column(
        name = "${columnName}",
        <#if field.validations.unique?? && field.validations.unique>unique = true,</#if>
        <#if field.validations.required?? && field.validations.required>nullable = false</#if>
    )
        <#else>
    @Column(name = "${columnName}")
        </#if>
    private ${field.type} ${field.name};
    </#if>

</#list>
}