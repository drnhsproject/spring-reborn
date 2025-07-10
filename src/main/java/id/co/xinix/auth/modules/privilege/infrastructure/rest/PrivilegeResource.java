package id.co.xinix.auth.modules.privilege.infrastructure.rest;

import id.co.xinix.auth.modules.privilege.application.dto.*;
import id.co.xinix.auth.modules.privilege.application.usecase.*;
import id.co.xinix.auth.modules.privilege.domain.PrivilegeRepository;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/privileges")
@Tag(name = "Privilege API", description = "Operation privilege")
@AllArgsConstructor
@RepositoryRestResource(exported = false)
@SecurityRequirement(name = "bearerAuth")
public class PrivilegeResource {

    private final CreatePrivilege createPrivilege;

    private final IdValidationService idValidationService;

    private final PrivilegeRepository privilegeRepository;

    private final ChangePrivilegeDetail changePrivilegeDetail;

    private final GetListPrivilege getListPrivilege;

    private final GetPrivilegeDetailById getPrivilegeDetailById;

    private final ArchivePrivilege archivePrivilege;

    private final RemovePrivilege removePrivilege;

    private final RestorePrivilege restorePrivilege;

    private final GetPrivilegesFormatted getPrivilegesFormatted;

    @Operation(summary = "Create Privilege", description = "create new privilege")
    @PostMapping("")
    public ResponseEntity<SingleResponse<PrivilegeCreatedResult>> createPrivilege(
        @Valid @RequestBody PrivilegeCommand command
    ) throws URISyntaxException {
        PrivilegeCreatedResult result = createPrivilege.handle(command);
        SingleResponse<PrivilegeCreatedResult> response = new SingleResponse<>("privilege created", result);

        return ResponseEntity.created(new URI("/api/privileges/")).body(response);
    }

    @Operation(summary = "Change Privilege", description = "change detail privilege")
    @PutMapping("/{id}")
    public ResponseEntity<SingleResponse<PrivilegeUpdatedResult>> updatePrivilege(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PrivilegeCommand command
    ) {
        idValidationService.validateIdForUpdate(privilegeRepository, id, command.getId(), "privilege");

        PrivilegeUpdatedResult result = changePrivilegeDetail.handle(command);
        SingleResponse<PrivilegeUpdatedResult> response = new SingleResponse<>("privilege updated", result);

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "List Privilege", description = "get all data privilege")
    @GetMapping("")
    public ResponseEntity<ListResponse<PagedResult>> getAllPrivilege(
        @RequestParam(value = "!search", required = false) String search,
        @ModelAttribute QueryFilter queryFilter,
        Pageable pageable
    ) {
        queryFilter.setSearch(search);
        Page<PagedResult> results = getListPrivilege.handle(queryFilter, pageable);
        ListResponse<PagedResult> response = ListResponse.fromPage("privilege retrieved", results);

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Detail Privilege", description = "get detail privilege by id")
    @GetMapping("/{id}")
    public ResponseEntity<SingleResponse<PrivilegeDetailResult>> getUserDetail(@PathVariable("id") Long id) {
        PrivilegeDetailResult result = getPrivilegeDetailById.handle(id);
        SingleResponse<PrivilegeDetailResult> response = new SingleResponse<>("privilege detail retrieved", result);

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Soft delete/archive privilege", description = "archive data privilege")
    @PutMapping("/{id}/delete")
    public ResponseEntity<SingleResponse<PrivilegeResult>> softDeletePrivilege(@PathVariable("id") Long id) {
        PrivilegeResult result = archivePrivilege.handle(id);
        SingleResponse<PrivilegeResult> response = new SingleResponse<>("privilege archived", result);

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Restore privilege", description = "restore data privilege")
    @PutMapping("/{id}/restore")
    public ResponseEntity<SingleResponse<PrivilegeResult>> restorePrivilege(@PathVariable("id") Long id) {
        PrivilegeResult result = restorePrivilege.handle(id);
        SingleResponse<PrivilegeResult> response = new SingleResponse<>("privilege restored", result);

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Permanent delete/destroy privilege", description = "destroy data privilege")
    @DeleteMapping("/{id}/destroy")
    public ResponseEntity<SingleResponse<PrivilegeResult>> deletePrivilege(@PathVariable("id") Long id) {
        PrivilegeResult result = removePrivilege.handle(id);
        SingleResponse<PrivilegeResult> response = new SingleResponse<>("privilege destroyed", result);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/fetch/format")
    public ResponseEntity<Map<String, Object>> formatPrivileges() {
        List<Map<String, Object>> data = getPrivilegesFormatted.fetchGroupedPrivileges();

        Map<String, Object> response = new HashMap<>();
        response.put("data", data);

        return ResponseEntity.ok(response);
    }
}