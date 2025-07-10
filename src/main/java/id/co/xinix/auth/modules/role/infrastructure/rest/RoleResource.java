package id.co.xinix.auth.modules.role.infrastructure.rest;

import id.co.xinix.auth.modules.role.application.dto.*;
import id.co.xinix.auth.modules.role.application.usecase.*;
import id.co.xinix.auth.modules.role.domain.RoleRepository;
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
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@RestController
@RequestMapping("/api/roles")
@Tag(name = "Role API", description = "Operation role")
@AllArgsConstructor
@RepositoryRestResource(exported = false)
@SecurityRequirement(name = "bearerAuth")
public class RoleResource {

    private final CreateRole createRole;
    private final GetListRole getListRole;
    private final GetRoleDetailById getRoleDetailById;
    private final IdValidationService idValidationService;
    private final RoleRepository roleRepository;
    private final ChangeRoleDetail changeRoleDetail;
    private final ArchiveRole archiveRole;
    private final RemoveRole removeRole;
    private final RestoreRole restoreRole;

    @Operation(summary = "Create Role", description = "create new role")
    @PostMapping("")
    public ResponseEntity<SingleResponse<RoleCreatedResult>> createRole(
        @Valid @RequestBody RoleCommand command
    ) throws URISyntaxException {
        RoleCreatedResult result = createRole.handle(command);
        SingleResponse<RoleCreatedResult> response = new SingleResponse<>("role created", result);

        return ResponseEntity.created(new URI("/api/roles/")).body(response);
    }

    @Operation(summary = "Change Role", description = "change detail role")
    @PutMapping("/{id}")
    public ResponseEntity<SingleResponse<RoleUpdatedResult>> updateRole(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RoleCommand command
    ) {
        idValidationService.validateIdForUpdate(roleRepository, id, command.getId(), "role");

        RoleUpdatedResult result = changeRoleDetail.handle(id, command);
        SingleResponse<RoleUpdatedResult> response = new SingleResponse<>("role updated", result);

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "List Role", description = "get all data role")
    @GetMapping("")
    public ResponseEntity<ListResponse<PagedResult>> getAllRole(
        @RequestParam Map<String, String> params,
        Pageable pageable
    ) {
        QueryFilter queryFilter = new QueryFilter(params);
        Page<PagedResult> results = getListRole.handle(queryFilter, pageable);
        ListResponse<PagedResult> response = ListResponse.fromPage("role retrieved", results);

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Detail Role", description = "get detail role by id")
    @GetMapping("/{id}")
    public ResponseEntity<SingleResponse<RoleDetailResult>> getRoleDetail(@PathVariable("id") Long id) {
        RoleDetailResult result = getRoleDetailById.handle(id);
        SingleResponse<RoleDetailResult> response = new SingleResponse<>("role detail retrieved", result);

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Soft delete/archive Role", description = "archive role")
    @PutMapping("/{id}/delete")
    public ResponseEntity<SingleResponse<RoleResult>> softDeleteRole(@PathVariable("id") Long id) {
        RoleResult result = archiveRole.handle(id);
        SingleResponse<RoleResult> response = new SingleResponse<>("role archived", result);

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Restore Role", description = "restore role")
    @PutMapping("/{id}/restore")
    public ResponseEntity<SingleResponse<RoleResult>> restoreRole(@PathVariable("id") Long id) {
        RoleResult result = restoreRole.handle(id);
        SingleResponse<RoleResult> response = new SingleResponse<>("role restored", result);

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Permanent delete/destroy Role", description = "remove role")
    @DeleteMapping("/{id}/destroy")
    public ResponseEntity<SingleResponse<RoleResult>> deleteRole(@PathVariable("id") Long id) {
        RoleResult result = removeRole.handle(id);
        SingleResponse<RoleResult> response = new SingleResponse<>("role removed", result);

        return ResponseEntity.ok().body(response);
    }
}
