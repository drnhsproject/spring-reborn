package id.co.xinix.spring.modules.example.application.usecase;

import id.co.xinix.spring.UseCase;
import id.co.xinix.spring.exception.NotFoundException;
import id.co.xinix.spring.modules.example.application.dto.ExampleDetailResult;
import id.co.xinix.spring.modules.example.domain.service.ExampleService;

@UseCase
public class GetExampleDetailById {

    private final ExampleService exampleService;

    public GetExampleDetailById(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    public ExampleDetailResult handle(Long id) {
        return exampleService.findOne(id)
                .map(exists -> new ExampleDetailResult(
                        exists.getId(),
                        exists.getCode(),
                        exists.getName(),
                        exists.getAge()
                ))
                .orElseThrow(() -> new NotFoundException("example not found"));
    }
}
