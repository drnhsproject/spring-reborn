package com.sagara.spring.module.example.domain.service;

import com.sagara.spring.module.example.application.dto.ExampleDTO;
import com.sagara.spring.module.example.domain.Example;
import com.sagara.spring.module.example.domain.ExampleRepository;
import com.sagara.spring.module.example.domain.IExampleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ExampleServiceImpl implements ExampleService{
    private static final Logger log = LoggerFactory.getLogger(ExampleServiceImpl.class);

    private final ExampleRepository exampleRepository;

    private final IExampleMapper exampleMapper;

    public ExampleServiceImpl(ExampleRepository exampleRepository, IExampleMapper exampleMapper) {
        this.exampleRepository = exampleRepository;
        this.exampleMapper = exampleMapper;
    }

    @Override
    public ExampleDTO save(ExampleDTO exampleDTO) {
        Example entity = exampleMapper.toEntity(exampleDTO);
        entity = exampleRepository.save(entity);

        return exampleMapper.toDTO(entity);
    }

    @Override
    public ExampleDTO update(ExampleDTO exampleDTO) {
        Example entity = exampleMapper.toEntity(exampleDTO);
        entity = exampleRepository.save(entity);

        return exampleMapper.toDTO(entity);
    }

    @Override
    public Page<ExampleDTO> findAll(Pageable pageable) {
        exampleRepository.findAll(pageable).map(exampleMapper::toDTO);
        return null;
    }

    @Override
    public Optional<ExampleDTO> findOne(Long id) {
        return exampleRepository.findById(id).map(exampleMapper::toDTO);
    }

    @Override
    public void delete(Long id) {
        exampleRepository.deleteById(id);
    }
}
