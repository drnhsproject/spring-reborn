package id.co.xinix.auth.modules.user.infrastructure.rest;

import id.co.xinix.auth.modules.user.application.dto.*;
import id.co.xinix.auth.modules.user.application.usecase.GetList;
import id.co.xinix.auth.modules.user.application.usecase.GetUserDetailById;
import id.co.xinix.auth.modules.user.application.usecase.RegisterUser;
import id.co.xinix.auth.services.ListResponse;
import id.co.xinix.auth.services.SingleResponse;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/v1/users")
@Tag(name = "User API", description = "Operation user")
@AllArgsConstructor
public class UserResource {

    private final RegisterUser registerUser;

    private final GetList getList;

    private final GetUserDetailById getUserDetailById;

    @Operation(summary = "Register User", description = "Create new user")
    @PostMapping("")
    public ResponseEntity<SingleResponse<UserRegisteredResult>> registerUser(
            @Valid @RequestBody UserCommand command
    ) throws URISyntaxException {
        UserRegisteredResult result = registerUser.handle(command);
        SingleResponse<UserRegisteredResult> response = new SingleResponse<>("user registered", result);

        return ResponseEntity.created(new URI("/api/users/")).body(response);
    }

    @GetMapping("")
    public ResponseEntity<ListResponse<PagedResult>> getAllUser(
        @RequestParam(value = "!search", required = false) String search,
        @ModelAttribute QueryFilter queryFilter,
        Pageable pageable
    ) {
        queryFilter.setSearch(search);
        Page<PagedResult> results = getList.handle(queryFilter, pageable);
        ListResponse<PagedResult> response = new ListResponse<>(
            "user retrieved",
            results.getContent(),
            results.getTotalElements()
        );

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SingleResponse<UserDetailResult>> getExampleData(@PathVariable("id") Long id) {
        UserDetailResult result = getUserDetailById.handle(id);
        SingleResponse<UserDetailResult> response = new SingleResponse<>("user detail retrieved", result);

        return ResponseEntity.ok().body(response);
    }
}
