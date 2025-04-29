package id.co.xinix.spring.modules.example.application.usecase;

import id.co.xinix.spring.UseCase;
import id.co.xinix.spring.exception.NotFoundException;
import id.co.xinix.spring.modules.example.application.dto.ExampleCommand;
import id.co.xinix.spring.modules.example.application.dto.ExampleDTO;
import id.co.xinix.spring.modules.example.application.dto.ExampleUpdatedResult;
import id.co.xinix.spring.modules.example.domain.service.ExampleService;

@UseCase
public class ChangeExampleDetail {

    private final ExampleService exampleService;

    public ChangeExampleDetail(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    public ExampleUpdatedResult handle(ExampleCommand command) {
        ExampleDTO example = exampleService.findOne(command.getId())
                .map(exists -> {
                    exists.setName(command.getName());
                    exists.setAge(command.getAge());

                    return exists;
                })
                .orElseThrow(() -> new NotFoundException("example not found"));

        ExampleDTO updatedExample = exampleService.update(example);

        return new ExampleUpdatedResult(
                updatedExample.getId(),
                updatedExample.getCode(),
                updatedExample.getName(),
                updatedExample.getAge()
        );
    }
}
