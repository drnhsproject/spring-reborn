package com.sagara.spring.module.example.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExampleCommand {

    private Long id;

    private String name;

    private Integer age;
}
