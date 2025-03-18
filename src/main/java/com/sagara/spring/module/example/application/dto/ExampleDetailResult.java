package com.sagara.spring.module.example.application.dto;

import java.io.Serializable;

public record ExampleDetailResult(
   String name,
   Integer age,
   String address
) implements Serializable {}
