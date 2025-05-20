package id.co.xinix.auth.modules.authenticate.domain;

public record UserDetail(
        Long id,
        String username,
        String email,
        Integer status
) {
}
