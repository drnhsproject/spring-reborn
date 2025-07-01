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

import java.util.List;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/${entityKebabCase}s")
@RequiredArgsConstructor
public class ${entity.name}Resource {

    private final Create${entity.name} create;

    @PostMapping("")
    public ResponseEntity<SingleResponse<${entity.name}CreatedResult>> create${entity.name}(
        @Valid @RequestBody ${entity.name}Command command
    ) throws URISyntaxException
    {
        ${entity.name}CreatedResult result = create.handle(command);
        SingleResponse<${entity.name}CreatedResult> response = new SingleResponse<>("${entitySpacedLower} created", result);

        return ResponseEntity.created(new URI("/api/v1/${entityKebabCase}s/")).body(response);
    }
}
