package id.co.xinix.spring.framework;

public record JwtUserPrincipal(Long userId, String username) {
    @Override
    public String toString() {
        return username;
    }
}
