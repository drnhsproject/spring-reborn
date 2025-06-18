package id.co.xinix.spring.modules.sysparam.application.dto;

import jakarta.persistence.Query;

public record BaseQueryResult(Query query, Long count) {
}
