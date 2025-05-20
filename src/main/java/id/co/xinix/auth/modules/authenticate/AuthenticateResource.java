package id.co.xinix.auth.modules.authenticate;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticateResource {

    private final SignInUser signInUser;

    @PostMapping("/signin")
    public ResponseEntity<SignInResult> login(@Valid @RequestBody SignInCommand command) {
        SignInResult result = signInUser.handle(command);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(result.getAccessToken());
        return new ResponseEntity<>(result, httpHeaders, HttpStatus.OK);
    }
}
