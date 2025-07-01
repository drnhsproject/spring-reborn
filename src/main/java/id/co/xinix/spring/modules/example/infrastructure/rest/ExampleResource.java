package id.co.xinix.spring.modules.example.infrastructure.rest;

import id.co.xinix.spring.modules.example.application.dto.*;
import id.co.xinix.spring.modules.example.application.usecase.*;
import id.co.xinix.spring.modules.example.domain.ExampleRepository;
import id.co.xinix.spring.services.IdValidationService;
import id.co.xinix.spring.services.ListResponse;
import id.co.xinix.spring.services.SingleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/examples")
@Tag(name = "Example API", description = "Operation example")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class ExampleResource {

    private static final Logger log = LoggerFactory.getLogger(ExampleResource.class);

    private final ExampleRepository exampleRepository;

    private final IdValidationService idValidationService;

    private final CreateExample createExample;

    private final ChangeExampleDetail changeExampleDetail;

    private final GetExampleDetailById getExampleDetailById;

    private final GetList getList;

    private final ArchiveExample archiveExample;

    private final RemoveExample removeExample;

    @Operation(summary = "Create Example", description = "Create new example")
    @PostMapping("")
    public ResponseEntity<SingleResponse<ExampleCreatedResult>> createExample(@Valid @RequestBody ExampleCommand command)
            throws URISyntaxException {
        idValidationService.validateNotNull(command.getId());

        ExampleCreatedResult result = createExample.handle(command);
        SingleResponse<ExampleCreatedResult> response = new SingleResponse<>("example created", result);

        return ResponseEntity.created(new URI("/api/assessor-infos/")).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SingleResponse<ExampleUpdatedResult>> updateExample(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody ExampleCommand command
    ) {
        idValidationService.validateIdForUpdate(exampleRepository, id, command.getId(), "example");

        ExampleUpdatedResult result = changeExampleDetail.handle(command);
        SingleResponse<ExampleUpdatedResult> response = new SingleResponse<>("example updated", result);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("")
    public ResponseEntity<ListResponse<PagedResult>> getAllExample(
            @RequestParam(value = "!search", required = false) String search,
            @ModelAttribute QueryFilter queryFilter,
            Pageable pageable
    ) {
        queryFilter.setSearch(search);
        Page<PagedResult> results = getList.handle(queryFilter, pageable);
        ListResponse<PagedResult> response = ListResponse.fromPage("example retrieved", results);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SingleResponse<ExampleDetailResult>> getExampleDetail(@PathVariable("id") Long id) {
        ExampleDetailResult result = getExampleDetailById.handle(id);
        SingleResponse<ExampleDetailResult> response = new SingleResponse<>( "example detail retrieved", result);

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{id}/delete")
    public ResponseEntity<Void> softDeleteExample(@PathVariable("id") Long id) {
        archiveExample.handle(id);
        return ResponseEntity.noContent()
                .build();
    }

    @DeleteMapping("/{id}/destroy")
    public ResponseEntity<Void> deleteExample(@PathVariable("id") Long id) {
        removeExample.handle(id);
        return ResponseEntity.noContent()
                .build();
    }
}
