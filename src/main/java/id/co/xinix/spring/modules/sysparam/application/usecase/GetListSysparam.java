package id.co.xinix.spring.modules.sysparam.application.usecase;

import id.co.xinix.spring.UseCase;
import id.co.xinix.spring.modules.sysparam.application.dto.BaseQueryResult;
import id.co.xinix.spring.modules.sysparam.application.dto.PagedResult;
import id.co.xinix.spring.modules.sysparam.application.dto.QueryFilter;
import id.co.xinix.spring.modules.sysparam.infrastructure.presistence.BaseQueryGetList;
import id.co.xinix.spring.services.LikeOperatorResolver;
import id.co.xinix.spring.services.SqlQuoter;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.sql.SQLException;
import java.util.List;

@UseCase
@AllArgsConstructor
public class GetListSysparam {

    private final EntityManager entityManager;
    private final SqlQuoter sqlQuoter;
    private LikeOperatorResolver likeOperator;

    public Page<PagedResult> handle(QueryFilter queryFilter, Pageable pageable) throws SQLException {
        BaseQueryGetList baseQueryGetList = new BaseQueryGetList(entityManager, sqlQuoter, likeOperator);
        BaseQueryResult baseQueryResult = baseQueryGetList.execute(queryFilter, pageable);

        @SuppressWarnings("unchecked")
        List<PagedResult> results = baseQueryResult
            .query()
            .setFirstResult((int) pageable.getOffset())
            .setMaxResults(pageable.getPageSize())
            .getResultList();

        return new PageImpl<>(results, pageable, baseQueryResult.count());
    }
}
