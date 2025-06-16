package id.co.xinix.spring.modules.sysparam.application.dto;


import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class SysparamDTO implements Serializable {

    private Long id;

    private String group;

    private String key;

    private String value;

    private String longValue;

}