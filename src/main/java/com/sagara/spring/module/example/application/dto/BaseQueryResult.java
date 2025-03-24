package com.sagara.spring.module.example.application.dto;

import jakarta.persistence.Query;

public record BaseQueryResult(Query query, Long count) {
}
