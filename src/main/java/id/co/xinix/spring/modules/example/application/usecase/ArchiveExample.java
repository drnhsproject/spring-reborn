package id.co.xinix.spring.modules.example.application.usecase;

import id.co.xinix.spring.UseCase;
import id.co.xinix.spring.exception.NotFoundException;
import id.co.xinix.spring.modules.example.application.dto.ExampleDTO;
import id.co.xinix.spring.modules.example.domain.Example;
import id.co.xinix.spring.modules.example.domain.ExampleRepository;
import id.co.xinix.spring.modules.example.domain.service.ExampleService;
import lombok.AllArgsConstructor;

@UseCase
@AllArgsConstructor
public class ArchiveExample {

    private final ExampleRepository exampleRepository;

    public void handle(Long id) {
        Example example = exampleRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("example not found"));

        example.setIsActive(false);
        example.setStatus(0);

        exampleRepository.save(example);
    }
}
