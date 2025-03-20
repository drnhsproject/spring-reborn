package com.sagara.spring.module.example.domain.service;

import com.sagara.spring.module.example.application.dto.ExampleDTO;
import com.sagara.spring.module.example.domain.Example;
import com.sagara.spring.module.example.domain.ExampleRepository;
import com.sagara.spring.module.example.domain.IExampleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        log.debug("Request to save example : {} ", exampleDTO);
        Example entity = exampleMapper.toEntity(exampleDTO);
        entity = exampleRepository.save(entity);

        return exampleMapper.toDTO(entity);
    }
}
