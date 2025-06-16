package id.co.xinix.auth.modules.role.infrastructure.rest;

import id.co.xinix.auth.modules.role.application.dto.*;
import id.co.xinix.auth.modules.role.application.usecase.CreateRole;
import id.co.xinix.auth.modules.role.application.usecase.GetListRole;
import id.co.xinix.auth.modules.role.application.usecase.GetRoleDetailById;
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
@RequestMapping("/api/v1/roles")
@Tag(name = "Role API", description = "Operation role")
@AllArgsConstructor
public class RoleResource {

    private final CreateRole createRole;

    private final GetListRole getListRole;

    private final GetRoleDetailById getRoleDetailById;

    @Operation(summary = "Create Role", description = "Create new role")
    @PostMapping("")
    public ResponseEntity<SingleResponse<RoleCreatedResult>> createRole(
        @Valid @RequestBody RoleCommand command
    ) throws URISyntaxException {
        RoleCreatedResult result = createRole.handle(command);
        SingleResponse<RoleCreatedResult> response = new SingleResponse<>("role created", result);

        return ResponseEntity.created(new URI("/api/roles/")).body(response);
    }

    @GetMapping("")
    public ResponseEntity<ListResponse<PagedResult>> getAllRole(
        @RequestParam(value = "!search", required = false) String search,
        @ModelAttribute QueryFilter queryFilter,
        Pageable pageable
    ) {
        queryFilter.setSearch(search);
        Page<PagedResult> results = getListRole.handle(queryFilter, pageable);
        ListResponse<PagedResult> response = ListResponse.fromPage("role retrieved", results);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SingleResponse<RoleDetailResult>> getRoleDetail(@PathVariable("id") Long id) {
        RoleDetailResult result = getRoleDetailById.handle(id);
        SingleResponse<RoleDetailResult> response = new SingleResponse<>("role detail retrieved", result);

        return ResponseEntity.ok().body(response);
    }
}
