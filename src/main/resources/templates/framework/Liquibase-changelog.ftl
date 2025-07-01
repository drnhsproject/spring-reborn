<?xml version="1.0" encoding="UTF-8"?>
<databaseChangeLog
        xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
        https://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-latest.xsd">

    <changeSet id="${changelogId}" author="generator-spring">
            <createTable tableName="${entity.tableName}">
                <#list entity.fields as field>
                <column name="${field.name}" type="${field.sqlType}" <#if field.autoIncrement?? && field.autoIncrement>autoIncrement="true"</#if>>
                    <#assign isRequired = false>
                    <#if field.validations?? && field.validations.required?? && field.validations.required == true>
                      <#assign isRequired = true>
                    </#if>
                    <constraints<#if field.primaryKey?? && field.primaryKey> primaryKey="true"</#if><#if isRequired> nullable="false"<#else> nullable="true"</#if><#if field.validations?? && field.validations.unique?? && field.validations.unique == true> unique="true" uniqueConstraintName="uk_${entity.tableName}_${field.name}"</#if>/>
                </column>
                </#list>
                <column name="is_active" type="boolean">
                    <constraints nullable="false" />
                </column>
                <column name="created_by" type="varchar(255)">
                    <constraints nullable="true" />
                </column>
                <column name="updated_by" type="varchar(255)">
                    <constraints nullable="true" />
                </column>
                <column name="created_time" type="${r"${datetimeType}"}">
                    <constraints nullable="true" />
                </column>
                <column name="updated_time" type="${r"${datetimeType}"}">
                    <constraints nullable="true" />
                </column>
                <column name="status" type="integer">
                    <constraints nullable="true" />
                </column>
                <column name="value_1" type="varchar(5000)">
                    <constraints nullable="true" />
                </column>
                <column name="value_2" type="varchar(5000)">
                    <constraints nullable="true" />
                </column>
            </createTable>
        </changeSet>
</databaseChangeLog>