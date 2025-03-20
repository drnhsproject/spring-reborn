package com.sagara.spring.module.example.application.usecase;

import com.sagara.spring.UseCase;
import com.sagara.spring.exception.NotFoundException;
import com.sagara.spring.module.example.application.dto.ExampleCommand;
import com.sagara.spring.module.example.application.dto.ExampleDTO;
import com.sagara.spring.module.example.application.dto.ExampleUpdatedResult;
import com.sagara.spring.module.example.domain.service.ExampleService;

@UseCase
public class ChangeExampleDetail {

    private final ExampleService exampleService;

    public ChangeExampleDetail(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    public ExampleUpdatedResult handle(ExampleCommand command) {
        ExampleDTO example = exampleService.findOne(command.id())
                .map(exists -> {
                    exists.setName(command.name());
                    exists.setAge(command.age());

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
