package id.co.xinix.auth.modules.user.application.usecase;

import id.co.xinix.auth.UseCase;
import id.co.xinix.auth.modules.user.application.dto.BaseQueryResult;
import id.co.xinix.auth.modules.user.application.dto.PagedResult;
import id.co.xinix.auth.modules.user.application.dto.QueryFilter;
import id.co.xinix.auth.modules.user.infrastructure.presistence.BaseQueryGetList;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@UseCase("getListUser")
public class GetList {

    private final EntityManager entityManager;

    public GetList(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Page<PagedResult> handle(QueryFilter queryFilter, Pageable pageable) {
        BaseQueryGetList baseQueryGetList = new BaseQueryGetList(entityManager);
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
