package com.sagara.spring.module.example.infrastructure.rest;

import com.sagara.spring.module.example.application.dto.*;
import com.sagara.spring.module.example.application.usecase.*;
import com.sagara.spring.module.example.domain.ExampleRepository;
import com.sagara.spring.services.IdValidationService;
import com.sagara.spring.services.SingleResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final ArchiveExample archiveExample;

    private final RemoveExample removeExample;

    public ExampleResource(
            ExampleRepository exampleRepository,
            IdValidationService idValidationService,
            CreateExample createExample,
            ChangeExampleDetail changeExampleDetail,
            GetExampleDetailById getExampleDetailById,
            ArchiveExample archiveExample,
            RemoveExample removeExample
    ) {
        this.exampleRepository = exampleRepository;
        this.idValidationService = idValidationService;
        this.createExample = createExample;
        this.changeExampleDetail = changeExampleDetail;
        this.getExampleDetailById = getExampleDetailById;
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

//    @GetMapping("")
//    public ResponseEntity<ListResponse<AssessmentResults>> getAllExample(
//            @RequestParam(value = "!search", required = false) String search,
//            @ModelAttribute AssessmentQuery queryParams,
//            @org.springdoc.core.annotations.ParameterObject Pageable pageable
//    ) {
//        queryParams.setSearch(search);
//        Page<AssessmentResults> results = getAssessments.handle(queryParams, pageable);
//        ResponseWeb<AssessmentResults> responseWeb = getAssessments.toResponseWeb(results);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), results);
//        return ResponseEntity.ok().headers(headers).body(responseWeb);
//    }

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
