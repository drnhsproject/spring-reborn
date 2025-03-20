package com.sagara.spring.module.example.application.usecase;

import com.sagara.spring.UseCase;
import com.sagara.spring.exception.NotFoundException;
import com.sagara.spring.module.example.application.dto.ExampleDTO;
import com.sagara.spring.module.example.domain.service.ExampleService;

@UseCase
public class ArchiveExample {

    private final ExampleService exampleService;

    public ArchiveExample(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    public void handle(Long id) {
        ExampleDTO example = exampleService.findOne(id)
                .map(exists -> {
                    exists.setIsDeleted(true);
                    exists.setStatus(0);

                    return exists;
                }).orElseThrow(() -> new NotFoundException("example not found"));

        exampleService.update(example);
    }
}
