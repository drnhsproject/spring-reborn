package id.co.xinix.spring.framework;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Field {
    private String name;
    private String type;
    private Boolean primaryKey = false;
    private Boolean autoIncrement;
    private Object defaultValue;
    private FieldsValidation validations;
    private String sqlType;
}
