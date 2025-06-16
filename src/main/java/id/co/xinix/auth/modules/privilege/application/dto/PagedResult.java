package id.co.xinix.auth.modules.privilege.application.dto;

public record PagedResult(
    Long id,
    String module,
    String subModule,
    String ordering,
    String action,
    String method,
    String uri
) {}
