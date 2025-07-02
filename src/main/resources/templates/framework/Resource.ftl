package ${modulePackage}.infrastructure.rest;

import ${modulePackage}.application.dto.*;
import ${modulePackage}.application.usecase.*;
import ${modulePackage}.domain.${entity.name}Repository;
import ${basePackage}.services.ListResponse;
import ${basePackage}.services.SingleResponse;
import ${basePackage}.services.IdValidationService;

import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.Operation;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/${entityKebabCase}s")
@Tag(name = "${entitySpaced} API", description = "Operations related to ${entitySpacedLower}")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class ${entity.name}Resource {

    private final ${entity.name}Repository ${entityCamelCase}Repository;
    private final IdValidationService idValidationService;

    private final Create${entity.name} create;
    private final Get${entity.name}List get${entity.name}List;
    private final Change${entity.name}Detail change${entity.name}Detail;
    private final Get${entity.name}DetailById get${entity.name}DetailById;
    private final Archive${entity.name} archive${entity.name};
    private final Remove${entity.name} remove${entity.name};

    @Operation(summary = "Create ${entitySpacedLower}", description = "Create new ${entitySpacedLower}")
    @PostMapping("")
    public ResponseEntity<SingleResponse<${entity.name}CreatedResult>> create${entity.name}(
        @Valid @RequestBody ${entity.name}Command command
    ) throws URISyntaxException
    {
        idValidationService.validateNotNull(command.getId());

        ${entity.name}CreatedResult result = create.handle(command);
        SingleResponse<${entity.name}CreatedResult> response = new SingleResponse<>("${entitySpacedLower} created", result);

        return ResponseEntity.created(new URI("/api/${entityKebabCase}s/")).body(response);
    }

    @Operation(summary = "List ${entitySpacedLower}", description = "Retrieve a paginated list of ${entitySpacedLower}")
    @GetMapping("")
    public ResponseEntity<ListResponse<PagedResult>> getAll${entity.name}s(
            @RequestParam(value = "!search", required = false) String search,
            @ModelAttribute QueryFilter queryFilter,
            Pageable pageable
    ) {
        queryFilter.setSearch(search);
        Page<PagedResult> results = get${entity.name}List.handle(queryFilter, pageable);
        ListResponse<PagedResult> response = ListResponse.fromPage("${entitySpacedLower} retrieved", results);

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Update ${entitySpacedLower}", description = "Update an existing ${entitySpacedLower}")
    @PutMapping("/{id}")
    public ResponseEntity<SingleResponse<${entity.name}UpdatedResult>> update${entity.name}(
            @PathVariable(value = "id") final Long id,
            @RequestBody ${entity.name}Command command
    ) {
        idValidationService.validateIdForUpdate(${entityCamelCase}Repository, id, command.getId(), "${entityKebabCase}");

        ${entity.name}UpdatedResult result = change${entity.name}Detail.handle(command);
        SingleResponse<${entity.name}UpdatedResult> response = new SingleResponse<>("${entityKebabCase} updated", result);

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Get ${entitySpacedLower} detail", description = "Retrieve detailed information about a ${entitySpacedLower}")
    @GetMapping("/{id}")
    public ResponseEntity<SingleResponse<${entity.name}DetailResult>> get${entity.name}Detail(@PathVariable("id") Long id) {
        ${entity.name}DetailResult result = get${entity.name}DetailById.handle(id);
        SingleResponse<${entity.name}DetailResult> response = new SingleResponse<>("${entityKebabCase} detail retrieved", result);

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Soft delete ${entitySpacedLower}", description = "Soft delete a ${entitySpacedLower} by ID")
    @PutMapping("/{id}/delete")
    public ResponseEntity<Void> softDelete${entity.name}(@PathVariable("id") Long id) {
        archive${entity.name}.handle(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Hard delete ${entitySpacedLower}", description = "Permanently delete a ${entitySpacedLower} by ID")
    @DeleteMapping("/{id}/destroy")
    public ResponseEntity<Void> delete${entity.name}(@PathVariable("id") Long id) {
        remove${entity.name}.handle(id);
        return ResponseEntity.noContent().build();
    }
}
