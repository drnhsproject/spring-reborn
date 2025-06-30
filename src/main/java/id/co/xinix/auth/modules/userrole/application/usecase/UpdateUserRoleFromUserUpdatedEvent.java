package id.co.xinix.auth.modules.userrole.application.usecase;

import id.co.xinix.auth.UseCase;
import id.co.xinix.auth.modules.role.domain.Role;
import id.co.xinix.auth.modules.user.domain.UserUpdatedEvent;
import id.co.xinix.auth.modules.userrole.domain.UserRole;
import id.co.xinix.auth.modules.userrole.domain.UserRoleRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@UseCase
@AllArgsConstructor
public class UpdateUserRoleFromUserUpdatedEvent {

    private final UserRoleRepository userRoleRepository;

    public void handle(UserUpdatedEvent event) {
        List<Role> roles = event.getRoles();
        Long userId = event.getUserId();

        userRoleRepository.deleteByUserId(userId);

        List<UserRole> userRoles = roles.stream()
            .map(role -> {
                UserRole userRole = new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleCode(role.getCode());
                userRole.setRoleName(role.getName());
                return userRole;
            })
            .collect(Collectors.toList());

        userRoleRepository.saveAll(userRoles);
    }

}
