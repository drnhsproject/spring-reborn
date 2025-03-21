package com.sagara.spring.module.example.application.usecase;

import com.sagara.spring.UseCase;
import com.sagara.spring.module.example.domain.service.ExampleService;

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
