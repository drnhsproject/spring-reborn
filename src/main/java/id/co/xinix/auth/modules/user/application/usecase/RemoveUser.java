package id.co.xinix.auth.modules.user.application.usecase;

import id.co.xinix.auth.UseCase;
import id.co.xinix.auth.exception.NotFoundException;
import id.co.xinix.auth.modules.user.domain.UserRepository;
import id.co.xinix.auth.modules.userprofile.domain.UserProfileRepository;
import id.co.xinix.auth.modules.userrole.domain.UserRole;
import id.co.xinix.auth.modules.userrole.domain.UserRoleRepository;
import lombok.AllArgsConstructor;

import java.util.Set;

@UseCase
@AllArgsConstructor
public class RemoveUser {

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final UserProfileRepository userProfileRepository;

    public void handle(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("user not found");
        }

        Set<UserRole> userRoles = userRoleRepository.findByUserId(id);
        userRoles.forEach(userRoleRepository::delete);

        userProfileRepository.findByUserId(id)
            .ifPresent(userProfileRepository::delete);

        userRepository.deleteById(id);
    }
}
