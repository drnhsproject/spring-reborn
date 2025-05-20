package id.co.xinix.auth.modules.role.application.dto;

import id.co.xinix.auth.modules.privilege.domain.Privilege;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RoleCommand {
    private Long id;
    private String code;
    private String name;
    private List<Privilege> privileges;
}
