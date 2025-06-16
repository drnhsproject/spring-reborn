package id.co.xinix.spring.modules.sysparam.infrastructure.rest;

import id.co.xinix.spring.modules.sysparam.application.dto.*;
import id.co.xinix.spring.modules.sysparam.application.usecase.*;
import id.co.xinix.spring.services.SingleResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/sysparams")
@RequiredArgsConstructor
@Tag(name = "Sysparams", description = "Operation sysparams")
@SecurityRequirement(name = "bearerAuth")
public class SysparamResource {

    private final CreateSysparam create;

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
}
