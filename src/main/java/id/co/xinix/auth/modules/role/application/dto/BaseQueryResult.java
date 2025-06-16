package id.co.xinix.auth.modules.role.application.dto;

import jakarta.persistence.Query;

public record BaseQueryResult(Query query, Long count) {
}
