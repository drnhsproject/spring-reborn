package id.co.xinix.spring.modules.sysparam.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class SysparamCommand {

    private Long id;
    private String group;
    private String key;
    private String value;
    private String long_value;

}
