package id.co.xinix.auth.modules.user.infrastructure.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import id.co.xinix.auth.modules.user.application.dto.*;
import id.co.xinix.auth.modules.user.application.usecase.*;
import id.co.xinix.auth.modules.user.domain.UserRepository;
import id.co.xinix.auth.services.IdValidationService;
import id.co.xinix.auth.services.ListResponse;
import id.co.xinix.auth.services.SingleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User API", description = "Operation user")
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
public class UserResource {

    private final RegisterUser registerUser;

    private final GetUserDetailById getUserDetailById;

    private final GetList getList;

    private final IdValidationService idValidationService;

    private final UserRepository userRepository;

    private final ChangeUserDetail changeUserDetail;

    private final ArchiveUser archiveUser;

    private final RemoveUser removeUser;

    private final RestoreUser restoreUser;

    @Operation(summary = "Register User", description = "Create new user")
    @PostMapping("")
    public ResponseEntity<SingleResponse<UserRegisteredResult>> registerUser(
            @Valid @RequestBody UserCommand command
    ) throws URISyntaxException, JsonProcessingException {
        UserRegisteredResult result = registerUser.handle(command);
        SingleResponse<UserRegisteredResult> response = new SingleResponse<>("user registered", result);

        return ResponseEntity.created(new URI("/api/users/")).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SingleResponse<UserUpdatedResult>> updateUser(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UserUpdateCommand command
    ) throws JsonProcessingException {
        command.setId(id);
        idValidationService.validateIdForUpdate(userRepository, id, command.getId(), "user");

        UserUpdatedResult result = changeUserDetail.handle(id, command);
        SingleResponse<UserUpdatedResult> response = new SingleResponse<>("user updated", result);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("")
    public ResponseEntity<ListResponse<PagedResult>> getAllUser(
        @RequestParam(value = "!search", required = false) String search,
        @ModelAttribute QueryFilter queryFilter,
        Pageable pageable
    ) {
        queryFilter.setSearch(search);
        Page<PagedResult> results = getList.handle(queryFilter, pageable);
        ListResponse<PagedResult> response = ListResponse.fromPage("user retrieved", results);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SingleResponse<UserDetailResult>> getUserDetail(@PathVariable("id") Long id) throws JsonProcessingException {
        UserDetailResult result = getUserDetailById.handle(id);
        SingleResponse<UserDetailResult> response = new SingleResponse<>( "user detail retrieved", result);

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{id}/delete")
    public ResponseEntity<Void> softDeleteUser(@PathVariable("id") Long id) {
        archiveUser.handle(id);
        return ResponseEntity.noContent()
            .build();
    }

    @PutMapping("/{id}/restore")
    public ResponseEntity<Void> restoreUser(@PathVariable("id") Long id) {
        restoreUser.handle(id);
        return ResponseEntity.noContent()
            .build();
    }

    @DeleteMapping("/{id}/destroy")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        removeUser.handle(id);
        return ResponseEntity.noContent()
            .build();
    }
}
