package id.co.xinix.auth.modules.user.application.dto;

import java.util.List;

public record UserDetailResult(
    Long id,
    String username,
    String email,
    List<String> role,
    Integer status
) {}
