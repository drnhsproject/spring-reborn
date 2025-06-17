package id.co.xinix.auth.modules.role.application.dto;

import java.util.List;

public record RoleDetailResult(
  Long id,
  String code,
  String name,
  List<String> privilege
) {}
