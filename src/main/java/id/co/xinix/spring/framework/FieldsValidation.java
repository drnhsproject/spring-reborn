package id.co.xinix.spring.framework;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FieldsValidation {
        private Boolean required;
        private Boolean unique;
        private Integer minLength;
        private Integer maxLength;
        private Number min;
        private Number max;
}
