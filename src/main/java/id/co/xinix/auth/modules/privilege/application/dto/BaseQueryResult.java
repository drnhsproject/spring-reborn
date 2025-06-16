package id.co.xinix.auth.modules.privilege.application.dto;

import jakarta.persistence.Query;

public record BaseQueryResult(Query query, Long count) {
}
