package com.sagara.spring.module.example.application.usecase;

import com.sagara.spring.UseCase;
import com.sagara.spring.module.example.application.dto.BaseQueryResult;
import com.sagara.spring.module.example.application.dto.PagedResult;
import com.sagara.spring.module.example.application.dto.QueryFilter;
import com.sagara.spring.module.example.infrastructure.persistence.BaseQueryGetList;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@UseCase
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
