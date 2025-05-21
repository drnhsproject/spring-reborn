package id.co.xinix.auth.modules.roleprivilege.application;

import id.co.xinix.auth.modules.privilege.domain.Privilege;
import id.co.xinix.auth.modules.role.domain.RoleCreatedEvent;
import id.co.xinix.auth.modules.roleprivilege.domain.RolePrivilege;
import id.co.xinix.auth.modules.roleprivilege.domain.RolePrivilegeRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@AllArgsConstructor
@Transactional
public class OnRoleCreatedCreateRolePrivilege {

    private final RolePrivilegeRepository rolePrivilegeRepository;

    @EventListener
    public void handleRoleCreatedEvent(RoleCreatedEvent event) {
        String roleCode = event.getRoleCode();
        List<Privilege> privileges = event.getPrivileges();

        List<RolePrivilege> privilegeList = privileges.stream()
                .map(privilege -> {
                    RolePrivilege rolePrivilege = new RolePrivilege();
                    rolePrivilege.setRoleCode(roleCode);
                    rolePrivilege.setUri(privilege.getUri());
                    rolePrivilege.setMethod(privilege.getMethod());

                    return rolePrivilege;
                })
                .toList();

        rolePrivilegeRepository.saveAll(privilegeList);
    }
}
