package id.co.xinix.auth.config;

import id.co.xinix.auth.security.AuthoritiesConstants;
import id.co.xinix.auth.security.jwt.JwtProperties;
import id.co.xinix.auth.security.jwt.JwtTokenFilter;
import id.co.xinix.auth.security.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration("authSecurityConfiguration")
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

    private final JwtProperties jwtProperties;

    private final TokenProvider tokenProvider;

    private final UserDetailServiceImpl userDetailService;

    public SecurityConfiguration(
            JwtProperties jwtProperties,
            TokenProvider tokenProvider,
            UserDetailServiceImpl userDetailService
    ) {
        this.jwtProperties = jwtProperties;
        this.tokenProvider = tokenProvider;
        this.userDetailService = userDetailService;
    }

    @Bean(name = "authFilterChain")
    public SecurityFilterChain authFilterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        http
                .securityMatcher(new OrRequestMatcher(
                        new AntPathRequestMatcher("/api/auth/**"),
                        new AntPathRequestMatcher("/api/v1/users/**")
                ))
            .cors(withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .headers(
                headers ->
                    headers
                        .contentSecurityPolicy(csp -> csp.policyDirectives(jwtProperties.getContentSecurityPolicy()))
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                        .referrerPolicy(
                            referrer -> referrer.policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
                        )
            )
            .authorizeHttpRequests(
                authz ->
                    // prettier-ignore
                authz
                        .requestMatchers(mvc.pattern(HttpMethod.POST, "/api/auth/signin")).permitAll()
                        .requestMatchers(mvc.pattern(HttpMethod.POST, "/api/authenticate/user")).permitAll()
                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/authenticate")).permitAll()
                        .requestMatchers(mvc.pattern(HttpMethod.GET, "/api/authenticate/user")).permitAll()
                        .requestMatchers(mvc.pattern(HttpMethod.POST, "/api/account/forgot-password")).permitAll()
                        .requestMatchers(mvc.pattern(HttpMethod.POST, "/api/account/reset-password")).permitAll()
                        .requestMatchers(mvc.pattern("/api/v1/users/**")).authenticated()
                        .requestMatchers(mvc.pattern("/api/admin/**")).hasAuthority(AuthoritiesConstants.ADMIN)
                        .requestMatchers(mvc.pattern("/api/**")).authenticated()
                        .requestMatchers(mvc.pattern("/v3/api-docs/**")).hasAuthority(AuthoritiesConstants.ADMIN)
                        .requestMatchers(mvc.pattern("/management/health")).permitAll()
                        .requestMatchers(mvc.pattern("/management/health/**")).permitAll()
                        .requestMatchers(mvc.pattern("/management/info")).permitAll()
                        .requestMatchers(mvc.pattern("/management/prometheus")).permitAll()
                        .requestMatchers(mvc.pattern("/management/**")).hasAuthority(AuthoritiesConstants.ADMIN)
            )
            .addFilterBefore(new JwtTokenFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(
                exceptions ->
                    exceptions
                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
            )
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            String role = jwt.getClaim("role");

            if (role == null) return List.of();

            return List.of(new SimpleGrantedAuthority(role));
        });
        return converter;
    }
}
