package com.sagara.spring.module.example.application.dto;

public record PagedResult(
        Long id,
        String code,
        String name,
        Integer age
) {
}
