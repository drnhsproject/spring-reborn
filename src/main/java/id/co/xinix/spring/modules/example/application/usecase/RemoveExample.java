package id.co.xinix.spring.modules.example.application.usecase;

import id.co.xinix.auth.exception.NotFoundException;
import id.co.xinix.spring.UseCase;
import id.co.xinix.spring.modules.example.application.dto.ExampleResult;
import id.co.xinix.spring.modules.example.domain.Example;
import id.co.xinix.spring.modules.example.domain.ExampleRepository;
import lombok.AllArgsConstructor;

@UseCase
@AllArgsConstructor
public class RemoveExample {

    private final ExampleRepository exampleRepository;

    public ExampleResult handle(Long id) {
        if (!exampleRepository.existsById(id)) {
            throw new NotFoundException("example not found");
        }

        Example example = exampleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("example not found"));

        ExampleResult result = new ExampleResult(
            example.getId(),
            example.getCode(),
            example.getName(),
            example.getNik(),
            example.getStatus()
        );

        exampleRepository.deleteById(id);

        return result;
    }
}
