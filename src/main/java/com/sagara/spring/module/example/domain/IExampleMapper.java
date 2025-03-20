package com.sagara.spring.module.example.domain;

import com.sagara.spring.module.example.application.dto.ExampleCommand;
import com.sagara.spring.module.example.application.dto.ExampleDTO;
import com.sagara.spring.services.IDomainMapper;

public interface IExampleMapper extends IDomainMapper<ExampleDTO, Example, ExampleCommand> {
}
