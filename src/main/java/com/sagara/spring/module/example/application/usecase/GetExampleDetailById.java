package com.sagara.spring.module.example.application.usecase;

import com.sagara.spring.UseCase;
import com.sagara.spring.exception.NotFoundException;
import com.sagara.spring.module.example.application.dto.ExampleDetailResult;
import com.sagara.spring.module.example.domain.service.ExampleService;

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
