package id.co.xinix.spring.modules.example.application.usecase;

import id.co.xinix.spring.UseCase;
import id.co.xinix.spring.modules.example.application.dto.ExampleCommand;
import id.co.xinix.spring.modules.example.application.dto.ExampleCreatedResult;
import id.co.xinix.spring.modules.example.application.dto.ExampleDTO;
import id.co.xinix.spring.modules.example.application.IExampleMapper;
import id.co.xinix.spring.modules.example.domain.service.ExampleService;

@UseCase
public class CreateExample {

    private final ExampleService exampleService;

    private final IExampleMapper exampleMapper;

    public CreateExample(
            ExampleService exampleService,
            IExampleMapper exampleMapper
    ) {
        this.exampleService = exampleService;
        this.exampleMapper = exampleMapper;
    }

    public ExampleCreatedResult handle(ExampleCommand command) {
        ExampleDTO dto = exampleMapper.commandToDTO(command);
        ExampleDTO savedExample = exampleService.save(dto);

        return new ExampleCreatedResult(
                savedExample.getCode(),
                savedExample.getName(),
                savedExample.getAge()
        );
    }
}
