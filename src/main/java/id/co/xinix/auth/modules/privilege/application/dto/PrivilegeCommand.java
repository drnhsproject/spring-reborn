package id.co.xinix.auth.modules.privilege.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PrivilegeCommand {
    private Long id;
    private String uri;
    private String module;
    private String submodule;
    private String action;
    private String method;
    private String ordering;
}
