package ${modulePackage}.infrastructure.persistence;

import ${modulePackage}.application.dto.*;
import ${boundedBasePackage}.services.SortBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class BaseQueryGetList {

    private final EntityManager entityManager;

    public BaseQueryResult execute(QueryFilter queryFilter, Pageable pageable) {
        String baseSql =
                """
                SELECT
                    ${fieldColumns}
                FROM
                    ${entity.tableName}
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
                        ${entity.tableName}
                """;

        String whereClause = queryFilter.buildWhereClause();

        String querySql = baseSql + whereClause;
        Query countQuery = entityManager.createNativeQuery(querySql);

        queryFilter.applyBindingParameters(countQuery);

        return (Long) countQuery.getSingleResult();
    }
}