package com.sagara.spring.module.example.application;

import com.sagara.spring.module.example.application.dto.ExampleCommand;
import com.sagara.spring.module.example.application.dto.ExampleCreatedResult;
import com.sagara.spring.module.example.application.dto.ExampleDetailResult;
import com.sagara.spring.module.example.application.dto.ExampleUpdatedResult;
import com.sagara.spring.module.example.application.usecase.ChangeExampleDetail;
import com.sagara.spring.module.example.application.usecase.CreateExample;
import com.sagara.spring.module.example.application.usecase.GetExampleDetailById;
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

    public ExampleResource(
            ExampleRepository exampleRepository,
            IdValidationService idValidationService,
            CreateExample createExample,
            ChangeExampleDetail changeExampleDetail,
            GetExampleDetailById getExampleDetailById
    ) {
        this.exampleRepository = exampleRepository;
        this.idValidationService = idValidationService;
        this.createExample = createExample;
        this.changeExampleDetail = changeExampleDetail;
        this.getExampleDetailById = getExampleDetailById;
    }


    @PostMapping("")
    public ResponseEntity<SingleResponse<ExampleCreatedResult>> createExample(@Valid @RequestBody ExampleCommand command)
            throws URISyntaxException {

        idValidationService.validateNotNull(command.id());

        ExampleCreatedResult result = createExample.handle(command);
        SingleResponse<ExampleCreatedResult> response = new SingleResponse<>("example created", result);

        return ResponseEntity.created(new URI("/api/assessor-infos/")).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SingleResponse<ExampleUpdatedResult>> updateExample(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody ExampleCommand command
    ) {
        idValidationService.validateIdForUpdate(exampleRepository, id, command.id(), "example");

        ExampleUpdatedResult result = changeExampleDetail.handle(command);
        SingleResponse<ExampleUpdatedResult> response = new SingleResponse<>("example updated", result);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SingleResponse<ExampleDetailResult>> getExampleDetail(@PathVariable("id") Long id) {
        ExampleDetailResult result = getExampleDetailById.handle(id);
        SingleResponse<ExampleDetailResult> response = new SingleResponse<>( "example detail retrieved", result);

        return ResponseEntity.ok().body(response);
    }
}
