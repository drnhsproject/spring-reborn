package com.sagara.spring.module.example.domain.service;

import com.sagara.spring.module.example.application.dto.ExampleDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ExampleService {

    ExampleDTO save(ExampleDTO exampleDTO);

    ExampleDTO update(ExampleDTO exampleDTO);

    Optional<ExampleDTO> partialUpdate(ExampleDTO exampleDTO);

    Page<ExampleDTO> findAll(ExampleDTO exampleDTO);

    Optional<ExampleDTO> findOne(Long id);

    void delete(Long id);
}
