package id.co.xinix.spring.modules.sysparam.infrastructure.presistence;

import id.co.xinix.spring.modules.sysparam.application.dto.BaseQueryResult;
import id.co.xinix.spring.modules.sysparam.application.dto.PagedResult;
import id.co.xinix.spring.modules.sysparam.application.dto.QueryFilter;
import id.co.xinix.spring.services.LikeOperatorResolver;
import id.co.xinix.spring.services.SortBuilder;
import id.co.xinix.spring.services.SqlQuoter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository("sysparamBaseQueryGetList")
@AllArgsConstructor
public class BaseQueryGetList {

    private final EntityManager entityManager;
    private final SqlQuoter quoter;
    private LikeOperatorResolver likeOperator;

    public BaseQueryResult execute(QueryFilter queryFilter, Pageable pageable) throws SQLException {
        queryFilter.setQuoter(quoter);
        queryFilter.setLikeOperator(likeOperator);

        String baseSql = """
            SELECT id, %s, %s, %s, long_value FROM sysparams
        """.formatted(
                quoter.quote("group"),
                quoter.quote("key"),
                quoter.quote("value")
        );

        String whereClause = queryFilter.buildWhereClause();
        String sort = SortBuilder.buildOrderByClause(pageable);

        String finalSql = baseSql + whereClause + sort;

        Query query = entityManager.createNativeQuery(finalSql, PagedResult.class);
        queryFilter.applyBindingParameters(query);

        Long count = countGetList(queryFilter);

        return new BaseQueryResult(query, count);
    }

    private Long countGetList(QueryFilter queryFilter) {
        String baseSql = """
                SELECT
                    COUNT(1)
                FROM
                    sysparams
            """;

        String whereClause = queryFilter.buildWhereClause();

        String querySql = baseSql + whereClause;
        Query countQuery = entityManager.createNativeQuery(querySql);

        queryFilter.applyBindingParameters(countQuery);

        return (Long) countQuery.getSingleResult();
    }
}
