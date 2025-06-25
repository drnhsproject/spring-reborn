package id.co.xinix.spring.modules.userprofile.infrastructure.rest;

import id.co.xinix.spring.modules.userprofile.application.dto.*;
import id.co.xinix.spring.modules.userprofile.application.usecase.*;
import id.co.xinix.spring.services.ListResponse;
import id.co.xinix.spring.services.SingleResponse;

import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import java.net.URI;
import java.net.URISyntaxException;

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
