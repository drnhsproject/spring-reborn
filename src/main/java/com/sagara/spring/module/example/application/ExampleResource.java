package com.sagara.spring.module.example.application;

import com.sagara.spring.exception.DomainException;
import com.sagara.spring.module.example.application.dto.ExampleCommand;
import com.sagara.spring.module.example.application.dto.ExampleCreatedResult;
import com.sagara.spring.module.example.application.dto.ExampleDTO;
import com.sagara.spring.module.example.application.usecase.CreateExample;
import com.sagara.spring.module.example.domain.ExampleRepository;
import com.sagara.spring.module.example.domain.service.ExampleService;
import com.sagara.spring.services.SingleResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.HeaderUtil;
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

    public ExampleResource(CreateExample createExample) {
        this.createExample = createExample;
    }

    @PostMapping("")
    public ResponseEntity<SingleResponse<ExampleCreatedResult>> createAssessorInfo(@Valid @RequestBody ExampleCommand command)
            throws URISyntaxException {

        if (command.id() != null) {
            throw new DomainException("id exists");
        }

        ExampleCreatedResult result = createExample.handle(command);
        SingleResponse<ExampleCreatedResult> response = new SingleResponse<>("example created", result);
        return ResponseEntity.created(new URI("/api/assessor-infos/"))
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SingleResponse<String>> getExample(@PathVariable("id") Long id) {
        String result = "This data example";
        SingleResponse<String> response = new SingleResponse<>( "Example detail retrieved",result);
        return ResponseEntity.ok().body(response);
    }
}
