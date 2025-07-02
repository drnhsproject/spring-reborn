package ${modulePackage}.application.dto;

import jakarta.persistence.Query;

public record BaseQueryResult(Query query, Long count) {
}