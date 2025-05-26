package id.co.xinix.auth.modules.user.application.dto;

import jakarta.persistence.Query;

public record BaseQueryResult(Query query, Long count) {
}
