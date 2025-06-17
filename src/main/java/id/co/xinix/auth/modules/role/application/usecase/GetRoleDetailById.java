package id.co.xinix.auth.modules.role.application.usecase;

import id.co.xinix.auth.UseCase;
import id.co.xinix.auth.exception.NotFoundException;
import id.co.xinix.auth.modules.role.application.dto.RoleDetailResult;
import id.co.xinix.auth.modules.role.domain.Role;
import id.co.xinix.auth.modules.role.domain.RoleRepository;
import id.co.xinix.auth.modules.roleprivilege.domain.RolePrivilege;
import id.co.xinix.auth.modules.roleprivilege.domain.RolePrivilegeRepository;

import java.util.List;
import java.util.stream.Collectors;

@UseCase
public class GetRoleDetailById {

    private final RoleRepository roleRepository;

    private final RolePrivilegeRepository rolePrivilegeRepository;

    public GetRoleDetailById(RoleRepository roleRepository, RolePrivilegeRepository rolePrivilegeRepository) {
        this.roleRepository = roleRepository;
        this.rolePrivilegeRepository = rolePrivilegeRepository;
    }

    public RoleDetailResult handle(Long id) {
        Role role = roleRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("role not found"));

        List<String> privileges = getPrivilegesSelectedRole(role);

        return mapToDto(role, privileges);
    }

    private List<String> getPrivilegesSelectedRole(Role role) {
        List<RolePrivilege> rolePrivileges = rolePrivilegeRepository.findByRoleCode(role.getCode());

        return rolePrivileges.stream().map(privilege ->privilege.getUri() + "|" + privilege.getMethod()).collect(Collectors.toList());
    }

    private RoleDetailResult mapToDto(Role role, List<String> privileges) {
        return new RoleDetailResult(
            role.getId(),
            role.getCode(),
            role.getName(),
            privileges
        );
    }
}
