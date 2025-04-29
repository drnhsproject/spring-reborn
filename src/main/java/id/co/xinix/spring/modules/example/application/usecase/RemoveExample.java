package id.co.xinix.spring.modules.example.application.usecase;

import id.co.xinix.spring.UseCase;
import id.co.xinix.spring.modules.example.domain.service.ExampleService;

@UseCase
public class RemoveExample {

    private final ExampleService exampleService;

    public RemoveExample(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    public void handle(Long id) {
        exampleService.delete(id);
    }
}
