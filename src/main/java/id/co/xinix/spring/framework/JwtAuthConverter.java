package id.co.xinix.spring.framework;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;

public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        String username = jwt.getSubject();
        Long userId = jwt.getClaim("userId");
        String role = jwt.getClaim("role");

        Collection<GrantedAuthority> authorities = role != null
                ? List.of(new SimpleGrantedAuthority(role))
                : List.of();

        JwtUserPrincipal principal = new JwtUserPrincipal(userId, username);

        return new UsernamePasswordAuthenticationToken(principal, "N/A", authorities);
    }
}

