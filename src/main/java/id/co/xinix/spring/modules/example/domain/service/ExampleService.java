package id.co.xinix.spring.modules.example.domain.service;

import id.co.xinix.spring.modules.example.application.dto.ExampleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ExampleService {

    ExampleDTO save(ExampleDTO exampleDTO);

    ExampleDTO update(ExampleDTO exampleDTO);

    Page<ExampleDTO> findAll(Pageable pageable);

    Optional<ExampleDTO> findOne(Long id);

    void delete(Long id);
}
