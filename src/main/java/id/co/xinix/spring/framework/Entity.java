package id.co.xinix.spring.framework;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Entity {
    private String name;
    private String tableName;
    private List<Field> fields;
}
