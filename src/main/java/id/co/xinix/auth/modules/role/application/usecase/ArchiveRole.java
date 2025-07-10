package id.co.xinix.auth.modules.role.application.usecase;

import id.co.xinix.auth.UseCase;
import id.co.xinix.auth.exception.NotFoundException;
import id.co.xinix.auth.modules.role.application.dto.RoleResult;
import id.co.xinix.auth.modules.role.domain.Role;
import id.co.xinix.auth.modules.role.domain.RoleRepository;
import id.co.xinix.auth.modules.roleprivilege.domain.RolePrivilege;
import id.co.xinix.auth.modules.roleprivilege.domain.RolePrivilegeRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@UseCase
@AllArgsConstructor
public class ArchiveRole {

    private final RoleRepository roleRepository;

    private final RolePrivilegeRepository rolePrivilegeRepository;

    public RoleResult handle(Long id) {
        Role role = roleRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("role not found"));

        role.setIsActive(false);
        role.setStatus(0);

        roleRepository.save(role);

        List<RolePrivilege> rolePrivileges = rolePrivilegeRepository.findByRoleCode(role.getCode());
        for (RolePrivilege privilege : rolePrivileges) {
            privilege.setIsActive(false);
            privilege.setStatus(0);
            rolePrivilegeRepository.save(privilege);
        }

        return new RoleResult(
            role.getId(),
            role.getCode(),
            role.getName(),
            role.getStatus()
        );
    }
}
