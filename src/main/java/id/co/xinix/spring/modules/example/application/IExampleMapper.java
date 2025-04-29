package id.co.xinix.spring.modules.example.application;

import id.co.xinix.spring.modules.example.application.dto.ExampleCommand;
import id.co.xinix.spring.modules.example.application.dto.ExampleDTO;
import id.co.xinix.spring.modules.example.domain.Example;
import id.co.xinix.spring.services.IDomainMapper;

public interface IExampleMapper extends IDomainMapper<ExampleDTO, Example, ExampleCommand> {
}
