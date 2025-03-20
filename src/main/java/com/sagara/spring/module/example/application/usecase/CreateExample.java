package com.sagara.spring.module.example.application.usecase;

import com.sagara.spring.UseCase;
import com.sagara.spring.module.example.application.dto.ExampleCommand;
import com.sagara.spring.module.example.application.dto.ExampleCreatedResult;
import com.sagara.spring.module.example.application.dto.ExampleDTO;
import com.sagara.spring.module.example.domain.IExampleMapper;
import com.sagara.spring.module.example.domain.service.ExampleService;

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
