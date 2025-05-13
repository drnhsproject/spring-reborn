package id.co.xinix.spring.modules.book.application.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class BookDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String name;

    @Min(value = 0)
    private BigDecimal price;

    private Boolean inStock = false;

}