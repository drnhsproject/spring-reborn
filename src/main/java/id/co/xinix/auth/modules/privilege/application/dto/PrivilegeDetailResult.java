package id.co.xinix.auth.modules.privilege.application.dto;

public record PrivilegeDetailResult(
   Long id,
   String module,
   String submodule,
   String ordering,
   String action,
   String method,
   String uri,
   Boolean isActive,
   Number status
) {}
