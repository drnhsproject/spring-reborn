package id.co.xinix.spring.services;

import org.springframework.data.domain.Pageable;

import java.util.stream.Collectors;

public class SortBuilder {

    public static String buildOrderByClause(Pageable pageable) {
        String sortOrder = pageable
            .getSort()
            .stream()
            .map(order -> order.getProperty() + " " + order.getDirection().name())
            .collect(Collectors.joining(", "));

        return sortOrder.isEmpty() ? "" : " ORDER BY " + sortOrder;
    }
}
