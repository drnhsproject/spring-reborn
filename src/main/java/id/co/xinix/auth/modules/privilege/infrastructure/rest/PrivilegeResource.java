package id.co.xinix.auth.modules.privilege.infrastructure.rest;

import id.co.xinix.auth.modules.privilege.application.dto.*;
import id.co.xinix.auth.modules.privilege.application.usecase.*;
import id.co.xinix.auth.modules.privilege.domain.PrivilegeRepository;
import id.co.xinix.auth.services.IdValidationService;
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
@RequestMapping("/api/v1/privileges")
@Tag(name = "Privilege API", description = "Operation privilege")
@AllArgsConstructor
public class PrivilegeResource {

    private final CreatePrivilege createPrivilege;

    private final IdValidationService idValidationService;

    private final PrivilegeRepository privilegeRepository;

    private final ChangePrivilegeDetail changePrivilegeDetail;

    private final GetListPrivilege getListPrivilege;

    private final GetPrivilegeDetailById getPrivilegeDetailById;

    private final ArchivePrivilege archivePrivilege;

    @Operation(summary = "Create Privilege", description = "Create new privilege")
    @PostMapping("")
    public ResponseEntity<SingleResponse<PrivilegeCreatedResult>> createPrivilege(
        @Valid @RequestBody PrivilegeCommand command
    ) throws URISyntaxException {
        PrivilegeCreatedResult result = createPrivilege.handle(command);
        SingleResponse<PrivilegeCreatedResult> response = new SingleResponse<>("privilege created", result);

        return ResponseEntity.created(new URI("/api/privileges/")).body(response);
    }

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

    @GetMapping("")
    public ResponseEntity<ListResponse<PagedResult>> getAllPrivilege(
        @RequestParam(value = "!search", required = false) String search,
        @ModelAttribute QueryFilter queryFilter,
        Pageable pageable
    ) {
        queryFilter.setSearch(search);
        Page<PagedResult> results = getListPrivilege.handle(queryFilter, pageable);
        ListResponse<PagedResult> response = new ListResponse<>(
            "privilege retrieved",
            results.getContent(),
            results.getTotalElements()
        );

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SingleResponse<PrivilegeDetailResult>> getUserDetail(@PathVariable("id") Long id) {
        PrivilegeDetailResult result = getPrivilegeDetailById.handle(id);
        SingleResponse<PrivilegeDetailResult> response = new SingleResponse<>("privilege detail retrieved", result);

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{id}/delete")
    public ResponseEntity<Void> softDeletePrivilege(@PathVariable("id") Long id) {
        archivePrivilege.handle(id);
        return ResponseEntity.noContent()
            .build();
    }
}
