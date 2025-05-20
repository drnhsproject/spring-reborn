package id.co.xinix.auth.modules.authenticate.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RolePrivilegeDetail {

    private List<RoleDetail> roles = new ArrayList<>();

    private List<PrivilegeDetail> privileges = new ArrayList<>();
}
