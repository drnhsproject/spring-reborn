package id.co.xinix.spring.modules.example.application.usecase;

import id.co.xinix.spring.UseCase;
import id.co.xinix.spring.modules.example.application.dto.ExampleCommand;
import id.co.xinix.spring.modules.example.application.dto.ExampleCreatedResult;
import id.co.xinix.spring.modules.example.domain.Example;
import id.co.xinix.spring.modules.example.domain.ExampleRepository;
import id.co.xinix.spring.services.GenerateRandomCode;

@UseCase
public class CreateExample {

    private final ExampleRepository exampleRepository;


    public CreateExample(
            ExampleRepository exampleRepository
    ) {
        this.exampleRepository = exampleRepository;
    }

    public ExampleCreatedResult handle(ExampleCommand command) {
        String code = new GenerateRandomCode().generate("EXP_");

        Example example = new Example();
        example.setCode(code);
        example.setName(command.getName());
        example.setAge(command.getAge());

        Example savedExample = exampleRepository.save(example);

        if (savedExample == null) {
            throw new IllegalStateException("failed to save example");
        }

        return new ExampleCreatedResult(
                savedExample.getCode(),
                savedExample.getName(),
                savedExample.getAge()
        );
    }
}
