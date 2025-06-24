package id.co.xinix.auth.modules.privilege.application.dto;

public record PrivilegeUpdatedResult(
    Long id,
    String uri,
    String module,
    String submodule,
    String action,
    String method,
    String ordering
) {}
