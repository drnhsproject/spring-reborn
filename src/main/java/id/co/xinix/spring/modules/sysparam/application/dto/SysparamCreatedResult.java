package id.co.xinix.spring.modules.sysparam.application.dto;


public record SysparamCreatedResult(
    Long id,
    String group,
    String key,
    String value,
    String longValue
) {
}