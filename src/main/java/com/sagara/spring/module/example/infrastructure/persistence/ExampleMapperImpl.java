package com.sagara.spring.module.example.infrastructure.persistence;

import com.sagara.spring.module.example.application.dto.ExampleCommand;
import com.sagara.spring.module.example.application.dto.ExampleDTO;
import com.sagara.spring.module.example.domain.Example;
import com.sagara.spring.module.example.domain.IExampleMapper;
import com.sagara.spring.services.GenerateRandomCode;
import org.springframework.stereotype.Component;

@Component
public class ExampleMapperImpl implements IExampleMapper {

    @Override
    public Example toEntity(ExampleDTO dto) {
        if (dto == null) {
            return null;
        }

        Example entity = new Example();
        entity.setId(dto.getId());
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
        entity.setAge(dto.getAge());
        entity.setIsDeleted(dto.getIsDeleted());
        entity.setStatus(dto.getStatus());

        return entity;
    }

    @Override
    public ExampleDTO toDTO(Example entity) {
        if (entity == null) {
            return null;
        }

        ExampleDTO dto = new ExampleDTO();
        dto.setId(entity.getId());
        dto.setCode(entity.getCode());
        dto.setName(entity.getName());
        dto.setAge(entity.getAge());
        dto.setIsDeleted(entity.getIsDeleted());
        dto.setStatus(entity.getStatus());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedTime(entity.getCreatedTime());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setUpdatedTime(entity.getUpdatedTime());

        return dto;
    }

    @Override
    public ExampleDTO commandToDTO(ExampleCommand command) {
        if (command == null) {
            return null;
        }

        String prefix = "EXP_";

        ExampleDTO dto = new ExampleDTO();
        dto.setCode(new GenerateRandomCode().generate(prefix));
        dto.setName(command.getName());
        dto.setAge(command.getAge());
        dto.setIsDeleted(false);
        dto.setStatus(1);

        return dto;
    }

    @Override
    public Example commandToEntity(ExampleCommand command) {
        return null;
    }

    @Override
    public ExampleDTO commandAndEntityToDTO(ExampleCommand command, Example entity) {
        return null;
    }

    @Override
    public Example commandAndDtoToEntity(ExampleCommand command, ExampleDTO dto) {
        return null;
    }
}
