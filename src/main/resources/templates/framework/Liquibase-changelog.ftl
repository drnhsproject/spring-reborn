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
                <constraints<#if field.primaryKey?? && field.primaryKey> primaryKey="true"</#if><#if field.required??> nullable="${!field.required?c}"</#if>/>
            </column>
            </#list>
        </createTable>
    </changeSet>

</databaseChangeLog>