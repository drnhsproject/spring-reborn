package id.co.xinix.spring.modules.book.application.dto;

import java.math.BigDecimal;

public record BookCreatedResult(
    Long id,
    String name,
    BigDecimal price,
    Boolean inStock
) {
}