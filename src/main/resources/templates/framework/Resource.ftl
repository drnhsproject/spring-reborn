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
@RequestMapping("/api/${entity.name?lower_case}s")
@RequiredArgsConstructor
public class ${entity.name}Resource {

    private final Create create;

    @PostMapping("")
    public ResponseEntity<SingleResponse<${entity.name}CreatedResult>> create${entity.name}(
        @Valid @RequestBody ${entity.name}Command command
    ) throws URISyntaxException
    {
        ${entity.name}CreatedResult result = create.handle(command);
        SingleResponse<${entity.name}CreatedResult> response = new SingleResponse<>("${entity.name?lower_case} created", result);

        return ResponseEntity.created(new URI("/api/${entity.name?lower_case}s/")).body(response);
    }

    @GetMapping
    public List<${entity.name}DTO> getAll${entity.name}s() {
        return ${entity.name?uncap_first}UseCase.findAll();
    }

    @GetMapping("/{id}")
    public ${entity.name}DTO get${entity.name}(@PathVariable Long id) {
        return ${entity.name?uncap_first}UseCase.findById(id);
    }

    @PostMapping
    public ${entity.name}DTO create${entity.name}(@RequestBody ${entity.name}DTO ${entity.name?uncap_first}DTO) {
        return ${entity.name?uncap_first}UseCase.create(${entity.name?uncap_first}DTO);
    }

    @PutMapping("/{id}")
    public ${entity.name}DTO update${entity.name}(@PathVariable Long id, @RequestBody ${entity.name}DTO ${entity.name?uncap_first}DTO) {
        return ${entity.name?uncap_first}UseCase.update(id, ${entity.name?uncap_first}DTO);
    }

    @DeleteMapping("/{id}")
    public void delete${entity.name}(@PathVariable Long id) {
        ${entity.name?uncap_first}UseCase.delete(id);
    }
}
