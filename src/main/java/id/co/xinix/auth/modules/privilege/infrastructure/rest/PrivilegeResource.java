package id.co.xinix.auth.modules.privilege.infrastructure.rest;

import id.co.xinix.auth.modules.privilege.application.dto.PrivilegeCommand;
import id.co.xinix.auth.modules.privilege.application.dto.PrivilegeCreatedResult;
import id.co.xinix.auth.modules.privilege.application.usecase.CreatePrivilege;
import id.co.xinix.auth.services.SingleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/v1/privileges")
@Tag(name = "Privilege API", description = "Operation privilege")
@AllArgsConstructor
public class PrivilegeResource {

    private final CreatePrivilege createPrivilege;

    @Operation(summary = "Create User", description = "Create new privilege")
    @PostMapping("")
    public ResponseEntity<SingleResponse<PrivilegeCreatedResult>> createUser(
        @Valid @RequestBody PrivilegeCommand command
    ) throws URISyntaxException {
        PrivilegeCreatedResult result = createPrivilege.handle(command);
        SingleResponse<PrivilegeCreatedResult> response = new SingleResponse<>("privilege created", result);

        return ResponseEntity.created(new URI("/api/privileges/")).body(response);
    }
}
