package id.co.xinix.auth.modules.privilege.infrastructure.rest;

import id.co.xinix.auth.modules.privilege.application.dto.PrivilegeCommand;
import id.co.xinix.auth.modules.privilege.application.dto.PrivilegeCreatedResult;
import id.co.xinix.auth.modules.privilege.application.dto.PrivilegeUpdatedResult;
import id.co.xinix.auth.modules.privilege.application.usecase.ChangePrivilegeDetail;
import id.co.xinix.auth.modules.privilege.application.usecase.CreatePrivilege;
import id.co.xinix.auth.modules.privilege.domain.PrivilegeRepository;
import id.co.xinix.auth.services.IdValidationService;
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
@RequestMapping("/api/v1/privileges")
@Tag(name = "Privilege API", description = "Operation privilege")
@AllArgsConstructor
public class PrivilegeResource {

    private final CreatePrivilege createPrivilege;

    private final IdValidationService idValidationService;

    private final PrivilegeRepository privilegeRepository;

    private final ChangePrivilegeDetail changePrivilegeDetail;

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
}
