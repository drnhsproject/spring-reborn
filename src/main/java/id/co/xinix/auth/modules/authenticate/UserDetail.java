package id.co.xinix.auth.modules.authenticate;

public record UserDetail(
        Long id,
        String username,
        String email,
        Integer status
) {
}
