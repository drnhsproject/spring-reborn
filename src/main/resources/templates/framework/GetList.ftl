package ${modulePackage}.application.usecase;

import ${boundedBasePackage}.UseCase;
import ${modulePackage}.application.dto.*;
import ${modulePackage}.infrastructure.persistence.BaseQueryGetList;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import lombok.AllArgsConstructor;

import java.util.List;

@UseCase
@AllArgsConstructor
public class Get${entity.name}List {

    private final EntityManager entityManager;

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