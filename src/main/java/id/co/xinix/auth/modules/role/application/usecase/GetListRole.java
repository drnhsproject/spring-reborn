package id.co.xinix.auth.modules.role.application.usecase;

import id.co.xinix.auth.UseCase;
import id.co.xinix.auth.modules.role.application.dto.BaseQueryResult;
import id.co.xinix.auth.modules.role.infrastructure.presistence.BaseQueryGetList;
import id.co.xinix.auth.modules.role.application.dto.PagedResult;
import id.co.xinix.auth.modules.role.application.dto.QueryFilter;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@UseCase
public class GetListRole {

    private final EntityManager entityManager;

    public GetListRole(EntityManager entityManager) {
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
