package id.co.xinix.spring.modules.sysparam.infrastructure.rest;

import id.co.xinix.spring.modules.sysparam.application.dto.*;
import id.co.xinix.spring.modules.sysparam.application.usecase.*;
import id.co.xinix.spring.modules.sysparam.domain.SysparamRepository;
import id.co.xinix.spring.services.IdValidationService;
import id.co.xinix.spring.services.ListResponse;
import id.co.xinix.spring.services.SingleResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

@RestController
@RequestMapping("/api/sysparams")
@RequiredArgsConstructor
@Tag(name = "Sysparams", description = "Operation sysparams")
@SecurityRequirement(name = "bearerAuth")
@RepositoryRestResource(exported = false)
public class SysparamResource {

    private final CreateSysparam create;

    private final GetListSysparam getListSysparam;

    private final GetSysparamDetailById getSysparamDetailById;

    private final ArchiveSysparam archiveSysparam;

    private final RemoveSysparam removeSysparam;

    private final IdValidationService idValidationService;

    private final SysparamRepository sysparamRepository;

    private final ChangeSysparamDetail changeSysparamDetail;

    private final RestoreSysparam restoreSysparam;

    @Operation(summary = "Create Sysparams", description = "Create new sysparams")
    @PostMapping("")
    public ResponseEntity<SingleResponse<SysparamCreatedResult>> createSysparam(
        @Valid @RequestBody SysparamCommand command
    ) throws URISyntaxException
    {
        SysparamCreatedResult result = create.handle(command);
        SingleResponse<SysparamCreatedResult> response = new SingleResponse<>("sysparam created", result);

        return ResponseEntity.created(new URI("/api/sysparams/")).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SingleResponse<SysparamUpdatedResult>> updateSysparam(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SysparamCommand command
    ) {
        idValidationService.validateIdForUpdate(sysparamRepository, id, command.getId(), "sysparam");

        SysparamUpdatedResult result = changeSysparamDetail.handle(command);
        SingleResponse<SysparamUpdatedResult> response = new SingleResponse<>("sysparam updated", result);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("")
    public ResponseEntity<ListResponse<PagedResult>> getAllSysparam(
        @RequestParam(value = "!search", required = false) String search,
        @ModelAttribute QueryFilter queryFilter,
        Pageable pageable
    ) throws SQLException {
        queryFilter.setSearch(search);
        Page<PagedResult> results = getListSysparam.handle(queryFilter, pageable);
        ListResponse<PagedResult> response = ListResponse.fromPage("sysparam retrieved", results);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SingleResponse<SysparamDetailResult>> getSysparamDetail(@PathVariable("id") Long id) {
        SysparamDetailResult result = getSysparamDetailById.handle(id);
        SingleResponse<SysparamDetailResult> response = new SingleResponse<>("sysparam detail retrieved", result);

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{id}/delete")
    public ResponseEntity<Void> softDeleteSysparam(@PathVariable("id") Long id) {
        archiveSysparam.handle(id);
        return ResponseEntity.noContent()
            .build();
    }

    @PutMapping("/{id}/restore")
    public ResponseEntity<Void> restoreSysparam(@PathVariable("id") Long id) {
        restoreSysparam.handle(id);
        return ResponseEntity.noContent()
            .build();
    }

    @DeleteMapping("/{id}/destroy")
    public ResponseEntity<Void> deleteSysparam(@PathVariable("id") Long id) {
        removeSysparam.handle(id);
        return ResponseEntity.noContent().build();
    }
}
