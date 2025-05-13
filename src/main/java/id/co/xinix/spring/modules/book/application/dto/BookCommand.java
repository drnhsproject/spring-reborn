package id.co.xinix.spring.modules.book.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class BookCommand {

    private Long id;
    private String name;
    private BigDecimal price;
    private Boolean inStock;

}
