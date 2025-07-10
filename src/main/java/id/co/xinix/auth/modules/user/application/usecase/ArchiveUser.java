package id.co.xinix.auth.modules.user.application.usecase;

import id.co.xinix.auth.UseCase;
import id.co.xinix.auth.exception.NotFoundException;
import id.co.xinix.auth.modules.user.application.dto.UserResult;
import id.co.xinix.auth.modules.user.domain.User;
import id.co.xinix.auth.modules.user.domain.UserRepository;
import id.co.xinix.auth.modules.userprofile.domain.UserProfile;
import id.co.xinix.auth.modules.userprofile.domain.UserProfileRepository;
import id.co.xinix.auth.modules.userrole.domain.UserRole;
import id.co.xinix.auth.modules.userrole.domain.UserRoleRepository;
import lombok.AllArgsConstructor;

import java.util.Set;

@UseCase
@AllArgsConstructor
public class ArchiveUser {

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final UserProfileRepository userProfileRepository;

    public UserResult handle(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("user not found"));

        user.setIsActive(false);
        user.setStatus(0);

        userRepository.save(user);

        Set<UserRole> userRoles = userRoleRepository.findByUserId(user.getId());
        for (UserRole userRole : userRoles) {
            userRole.setIsActive(false);
            userRole.setStatus(0);
            userRoleRepository.save(userRole);
        }

        UserProfile userProfile = userProfileRepository.findByUserId(user.getId())
            .orElse(null);

        if (userProfile != null) {
            userProfile.setIsActive(false);
            userProfile.setStatus(0);
            userProfileRepository.save(userProfile);
        }

        String firstName = userProfile == null ? "" : userProfile.getFirstName();
        String lastName = userProfile == null ? "" : userProfile.getLastName();

        return new UserResult(
                user.getId(),
                firstName,
                lastName,
                user.getUsername(),
                user.getEmail(),
                user.getStatus()
        );
    }
}
