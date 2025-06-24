package id.co.xinix.auth.modules.role.application.usecase;

import id.co.xinix.auth.UseCase;
import id.co.xinix.auth.exception.NotFoundException;
import id.co.xinix.auth.modules.role.application.dto.RoleCommand;
import id.co.xinix.auth.modules.role.application.dto.RoleUpdatedResult;
import id.co.xinix.auth.modules.role.domain.Role;
import id.co.xinix.auth.modules.role.domain.RoleRepository;
import id.co.xinix.auth.modules.roleprivilege.domain.RolePrivilege;
import id.co.xinix.auth.modules.roleprivilege.domain.RolePrivilegeRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@UseCase
@AllArgsConstructor
public class ChangeRoleDetail {

    private final RoleRepository roleRepository;

    private final RolePrivilegeRepository rolePrivilegeRepository;

    public RoleUpdatedResult handle(Long id, RoleCommand command) {
        Role role = roleRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("role not found"));

        role.setName(command.getName().toUpperCase());
        role.setCode(role.getCode());
        Role updatedRole = roleRepository.save(role);

        rolePrivilegeRepository.deleteByRoleCode(updatedRole.getCode());

        List<RolePrivilege> privileges = command
            .getPrivileges()
            .stream()
            .map(privilegeCmd -> {
                RolePrivilege privilege = new RolePrivilege();
                privilege.setRoleCode(updatedRole.getCode());
                privilege.setUri(privilegeCmd.getUri());
                privilege.setMethod(privilegeCmd.getMethod());
                return privilege;
            })
            .collect(Collectors.toList());

        rolePrivilegeRepository.saveAll(privileges);

        return new RoleUpdatedResult(
            updatedRole.getId(),
            updatedRole.getCode(),
            updatedRole.getName()
        );
    }
}
