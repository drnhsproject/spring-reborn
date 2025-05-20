package id.co.xinix.auth.modules.user.infrastructure.rest;

import id.co.xinix.auth.modules.user.application.dto.UserCommand;
import id.co.xinix.auth.modules.user.application.dto.UserRegisteredResult;
import id.co.xinix.auth.modules.user.application.usecase.RegisterUser;
import id.co.xinix.auth.services.SingleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User API", description = "Operation user")
@AllArgsConstructor
public class UserResource {

    private final RegisterUser registerUser;

    @Operation(summary = "Register User", description = "Create new user")
    @PostMapping("")
    public ResponseEntity<SingleResponse<UserRegisteredResult>> registerUser(
            @Valid @RequestBody UserCommand command
    ) throws URISyntaxException {
        UserRegisteredResult result = registerUser.handle(command);
        SingleResponse<UserRegisteredResult> response = new SingleResponse<>("user registered", result);

        return ResponseEntity.created(new URI("/api/users/")).body(response);
    }

}
