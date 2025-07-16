package id.co.xinix.auth.modules.authenticate.infrastructure.rest;

import id.co.xinix.auth.exception.ForbiddenException;
import id.co.xinix.auth.exception.UnauthorizedException;
import id.co.xinix.auth.modules.authenticate.application.dto.RefreshTokenCommand;
import id.co.xinix.auth.modules.authenticate.application.dto.RefreshTokenResult;
import id.co.xinix.auth.modules.authenticate.application.dto.SignInCommand;
import id.co.xinix.auth.modules.authenticate.application.dto.SignInResult;
import id.co.xinix.auth.modules.authenticate.application.usecase.SignInUser;
import id.co.xinix.auth.security.jwt.TokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Sign-in API", description = "Operation sing-in")
@RepositoryRestResource(exported = false)
@RequiredArgsConstructor
public class AuthenticateResource {

    private final TokenProvider tokenProvider;
    private final SignInUser signInUser;

    @Operation(summary = "Sign-in", description = "sign in to get access token")
    @PostMapping("/signin")
    public ResponseEntity<SignInResult> login(@Valid @RequestBody SignInCommand command) {
        SignInResult result = signInUser.handle(command);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(result.getAccessToken());
        return new ResponseEntity<>(result, httpHeaders, HttpStatus.OK);
    }

    @Operation(summary = "Refresh Token", description = "To refresh token")
    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResult> refreshAuthentication(
            @RequestHeader(value = "Authorization", required = false) String authHeader
    ) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("unauthorized");
        }

        String accessToken = authHeader.substring(7);
        String refreshedToken = tokenProvider.getRefreshToken(accessToken);

        return ResponseEntity.ok(new RefreshTokenResult(refreshedToken));
    }

}
