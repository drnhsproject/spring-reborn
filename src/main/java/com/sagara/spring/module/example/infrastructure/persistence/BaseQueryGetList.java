package com.sagara.spring.module.example.infrastructure.persistence;

import com.sagara.spring.module.example.application.dto.BaseQueryResult;
import com.sagara.spring.module.example.application.dto.PagedResult;
import com.sagara.spring.module.example.application.dto.QueryFilter;
import com.sagara.spring.services.SortBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.data.domain.Pageable;

public class BaseQueryGetList {

    private final EntityManager entityManager;

    public BaseQueryGetList(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public BaseQueryResult execute(QueryFilter queryFilter, Pageable pageable) {
        String baseSql =
                """
                    SELECT
                        id,
                        code,
                        name,
                        age
                    FROM
                        example
                """;

        String whereClause = queryFilter.buildWhereClause();
        String sort = SortBuilder.buildOrderByClause(pageable);

        String finalSql = baseSql + whereClause + sort;

        Query query = entityManager.createNativeQuery(finalSql, PagedResult.class);
        queryFilter.applyBindingParameters(query);

        Long count = countGetList(queryFilter);

        return new BaseQueryResult(query, count);
    }

    private Long countGetList(QueryFilter queryFilter) {
        String baseSql =
                """
                    SELECT
                        COUNT(1)
                    FROM
                        example
                """;

        String whereClause = queryFilter.buildWhereClause();

        String querySql = baseSql + whereClause;
        Query countQuery = entityManager.createNativeQuery(querySql);

        queryFilter.applyBindingParameters(countQuery);

        return (Long) countQuery.getSingleResult();
    }
}
