package com.sagara.spring.module.example.application;

import com.sagara.spring.module.example.application.dto.ExampleCommand;
import com.sagara.spring.module.example.application.dto.ExampleCreatedResult;
import com.sagara.spring.module.example.application.dto.ExampleUpdatedResult;
import com.sagara.spring.module.example.application.usecase.ChangeExampleDetail;
import com.sagara.spring.module.example.application.usecase.CreateExample;
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

    private final CreateExample createExample;

    private final ChangeExampleDetail changeExampleDetail;

    private final ExampleRepository exampleRepository;

    private final IdValidationService idValidationService;

    public ExampleResource(
            CreateExample createExample,
            ChangeExampleDetail changeExampleDetail,
            ExampleRepository exampleRepository,
            IdValidationService idValidationService
    ) {
        this.createExample = createExample;
        this.changeExampleDetail = changeExampleDetail;
        this.exampleRepository = exampleRepository;
        this.idValidationService = idValidationService;
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
    public ResponseEntity<SingleResponse<String>> getExample(@PathVariable("id") Long id) {
        String result = "This data example";
        SingleResponse<String> response = new SingleResponse<>( "Example detail retrieved",result);
        return ResponseEntity.ok().body(response);
    }
}
