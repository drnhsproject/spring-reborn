package id.co.xinix.auth.modules.role.infrastructure.rest;

import id.co.xinix.auth.modules.role.application.dto.RoleCommand;
import id.co.xinix.auth.modules.role.application.dto.RoleCreatedResult;
import id.co.xinix.auth.modules.role.application.usecase.CreateRole;
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
@RequestMapping("/api/v1/roles")
@Tag(name = "Role API", description = "Operation role")
@AllArgsConstructor
public class RoleResource {

    private final CreateRole createRole;

    @Operation(summary = "Create Role", description = "Create new role")
    @PostMapping("")
    public ResponseEntity<SingleResponse<RoleCreatedResult>> createRole(
        @Valid @RequestBody RoleCommand command
    ) throws URISyntaxException {
        RoleCreatedResult result = createRole.handle(command);
        SingleResponse<RoleCreatedResult> response = new SingleResponse<>("role created", result);

        return ResponseEntity.created(new URI("/api/roles/")).body(response);
    }
}
