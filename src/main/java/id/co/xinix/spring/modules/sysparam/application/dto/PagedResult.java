package id.co.xinix.spring.modules.sysparam.application.dto;

public record PagedResult(
   Long id,
   String group,
   String key,
   String value,
   String longValue
) {}
