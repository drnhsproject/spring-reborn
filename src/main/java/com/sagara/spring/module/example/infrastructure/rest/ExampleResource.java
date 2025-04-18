package com.sagara.spring.module.example.infrastructure.rest;

import com.sagara.spring.module.example.application.dto.*;
import com.sagara.spring.module.example.application.usecase.*;
import com.sagara.spring.module.example.domain.ExampleRepository;
import com.sagara.spring.services.IdValidationService;
import com.sagara.spring.services.ListResponse;
import com.sagara.spring.services.SingleResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/v1/examples")
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

    public ExampleResource(
            ExampleRepository exampleRepository,
            IdValidationService idValidationService,
            CreateExample createExample,
            ChangeExampleDetail changeExampleDetail,
            GetExampleDetailById getExampleDetailById,
            GetList getList,
            ArchiveExample archiveExample,
            RemoveExample removeExample
    ) {
        this.exampleRepository = exampleRepository;
        this.idValidationService = idValidationService;
        this.createExample = createExample;
        this.changeExampleDetail = changeExampleDetail;
        this.getExampleDetailById = getExampleDetailById;
        this.getList = getList;
        this.archiveExample = archiveExample;
        this.removeExample = removeExample;
    }

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
        ListResponse<PagedResult> response = new ListResponse<>(
                "example retrieved",
                results.getContent(),
                results.getTotalElements());

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
