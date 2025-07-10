package id.co.xinix.spring.modules.example.application.usecase;

import id.co.xinix.spring.UseCase;
import id.co.xinix.spring.exception.NotFoundException;
import id.co.xinix.spring.modules.example.application.dto.ExampleResult;
import id.co.xinix.spring.modules.example.domain.Example;
import id.co.xinix.spring.modules.example.domain.ExampleRepository;
import lombok.AllArgsConstructor;

@UseCase
@AllArgsConstructor
public class RestoreExample {

    private final ExampleRepository exampleRepository;

    public ExampleResult handle(Long id) {
        Example example = exampleRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("example not found"));

        example.setIsActive(true);
        example.setStatus(1);

        exampleRepository.save(example);

        return new ExampleResult(
            example.getId(),
            example.getCode(),
            example.getName(),
            example.getNik(),
            example.getStatus()
        );
    }
}
