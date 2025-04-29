package id.co.xinix.spring.modules.example.application.dto;

import jakarta.persistence.Query;

public record BaseQueryResult(Query query, Long count) {
}
