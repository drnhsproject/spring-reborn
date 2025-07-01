package id.co.xinix.auth.modules.userprofile.infrastructure.rest;

import id.co.xinix.auth.modules.userprofile.application.dto.UserProfileCommand;
import id.co.xinix.auth.modules.userprofile.application.dto.UserProfileCreatedResult;
import id.co.xinix.auth.modules.userprofile.application.usecase.CreateUserProfile;
import id.co.xinix.spring.services.SingleResponse;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;

@Hidden
@RestController
@RequestMapping("/api/user-profiles")
@RequiredArgsConstructor
public class UserProfileResource {

    private final CreateUserProfile create;

    @PostMapping("")
    public ResponseEntity<SingleResponse<UserProfileCreatedResult>> createUserProfile(
        @Valid @RequestBody UserProfileCommand command
    ) throws URISyntaxException
    {
        UserProfileCreatedResult result = create.handle(command);
        SingleResponse<UserProfileCreatedResult> response = new SingleResponse<>("user profile created", result);

        return ResponseEntity.created(new URI("/api/user-profiles/")).body(response);
    }
}
