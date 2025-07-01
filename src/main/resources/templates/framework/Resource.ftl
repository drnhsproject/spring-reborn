package ${modulePackage}.infrastructure.rest;

import ${modulePackage}.application.dto.*;
import ${modulePackage}.application.usecase.*;
import ${basePackage}.services.ListResponse;
import ${basePackage}.services.SingleResponse;

import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/${entityKebabCase}s")
@Tag(name = "${entity.name} API", description = "Operations related to ${entity.name}")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class ${entity.name}Resource {

    private final Create${entity.name} create;

    @Operation(summary = "Create ${entity.name}", description = "Create new ${entity.name}")
    @PostMapping("")
    public ResponseEntity<SingleResponse<${entity.name}CreatedResult>> create${entity.name}(
        @Valid @RequestBody ${entity.name}Command command
    ) throws URISyntaxException
    {
        ${entity.name}CreatedResult result = create.handle(command);
        SingleResponse<${entity.name}CreatedResult> response = new SingleResponse<>("${entitySpacedLower} created", result);

        return ResponseEntity.created(new URI("/api/${entityKebabCase}s/")).body(response);
    }
}
