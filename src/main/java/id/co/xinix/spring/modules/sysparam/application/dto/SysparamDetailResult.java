package id.co.xinix.spring.modules.sysparam.application.dto;

public record SysparamDetailResult(
   Long id,
   String group,
   String key,
   String value,
   String long_value
) {}
