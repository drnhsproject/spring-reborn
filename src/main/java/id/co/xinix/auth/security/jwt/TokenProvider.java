package id.co.xinix.auth.security.jwt;

import id.co.xinix.auth.exception.UnauthorizedException;
import id.co.xinix.auth.management.SecurityMetersService;
import id.co.xinix.auth.modules.authenticate.UserDetail;
import id.co.xinix.auth.modules.user.domain.User;
import id.co.xinix.auth.modules.user.domain.UserRepository;
import id.co.xinix.auth.modules.userrole.domain.UserRole;
import id.co.xinix.auth.modules.userrole.domain.UserRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static id.co.xinix.auth.security.SecurityUtils.JWT_ALGORITHM;

@Component
public class TokenProvider {

    private static final Logger log = LoggerFactory.getLogger(TokenProvider.class);
    private static final String AUTHORITIES_KEY = "auth";

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final SecurityMetersService securityMetersService;

    private final long tokenValidityInMilliseconds;
    private final long tokenValidityInMillisecondsForRememberMe;

    public TokenProvider(
            JwtProperties jwtProperties,
            JwtEncoder jwtEncoder,
            JwtDecoder jwtDecoder,
            UserRepository userRepository,
            UserRoleRepository userRoleRepository,
            SecurityMetersService securityMetersService
    ) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.securityMetersService = securityMetersService;

        this.tokenValidityInMilliseconds = jwtProperties.getTokenValidityInSeconds();
        this.tokenValidityInMillisecondsForRememberMe = jwtProperties.getTokenValidityInSecondsForRememberMe();
    }

    public String generateToken(Authentication authentication, boolean rememberMe, UserDetail userDetail) {
        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        User user = userRepository
                .findByUsername(userDetail.username())
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        String userRole = userRoleRepository
                .findByUserId(user.getId())
                .stream()
                .map(UserRole::getRoleName)
                .collect(Collectors.joining(","));

        Instant now = Instant.now();

        Instant validity = rememberMe
                ? now.plus(this.tokenValidityInMillisecondsForRememberMe, ChronoUnit.SECONDS)
                : now.plus(this.tokenValidityInMilliseconds, ChronoUnit.SECONDS);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .claim("id", userDetail.id())
                .claim("username", userDetail.username())
                .claim("email", userDetail.email())
                .claim("status", userDetail.status())
                .claim("role", userRole)
                .build();

        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }

    public Authentication getAuthentication(String token) {
        Jwt jwt = jwtDecoder.decode(token);
        List<SimpleGrantedAuthority> authorities = Arrays.stream(jwt.getClaimAsString(AUTHORITIES_KEY).split(","))
                .map(SimpleGrantedAuthority::new).toList();

        return new UsernamePasswordAuthenticationToken(jwt.getSubject(), token, authorities);
    }

    public String getRefreshToken(String token) {
        Jwt jwt = jwtDecoder.decode(token);

        if (jwt.getIssuedAt() != null) {
            throw new UnauthorizedException("refresh token invalid due to password change");
        }

        Long id = Long.parseLong(jwt.getClaimAsString("id"));

        UserDetail userDetail = new UserDetail(
                id,
                jwt.getClaimAsString("username"),
                jwt.getClaimAsString("email"),
                jwt.getClaim("status")
        );

        return generateToken(getAuthentication(token), false, userDetail);
    }

    public boolean validateToken(String token) {
        try {
            jwtDecoder.decode(token);
            return true;
        } catch (JwtException e) {
            log.warn("JWT validation failed: {}", e.getMessage());
            securityMetersService.trackTokenMalformed();
            return false;
        }
    }

    public Jwt decodeJwt(String token) {
        return jwtDecoder.decode(token);
    }
}
