package id.co.xinix.spring.modules.example.application.usecase;

import id.co.xinix.spring.UseCase;
import id.co.xinix.spring.exception.NotFoundException;
import id.co.xinix.spring.modules.example.application.dto.ExampleDTO;
import id.co.xinix.spring.modules.example.domain.service.ExampleService;

@UseCase
public class ArchiveExample {

    private final ExampleService exampleService;

    public ArchiveExample(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    public void handle(Long id) {
        ExampleDTO example = exampleService.findOne(id)
                .map(exists -> {
                    exists.setIsActive(true);
                    exists.setStatus(0);

                    return exists;
                }).orElseThrow(() -> new NotFoundException("example not found"));

        exampleService.update(example);
    }
}
