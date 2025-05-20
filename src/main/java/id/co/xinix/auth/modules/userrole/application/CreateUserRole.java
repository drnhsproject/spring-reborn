package id.co.xinix.auth.modules.userrole.application;

import id.co.xinix.auth.modules.role.domain.Role;
import id.co.xinix.auth.modules.user.application.event.UserRegisteredEvent;
import id.co.xinix.auth.modules.userrole.domain.UserRole;
import id.co.xinix.auth.modules.userrole.domain.UserRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Transactional
public class CreateUserRole {

    private final UserRoleRepository userRoleRepository;

    @EventListener
    public void handleUserRegisteredEvent(UserRegisteredEvent event) {
        List<Role> roles = event.getRoles();
        Long userId = event.getUserId();

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
