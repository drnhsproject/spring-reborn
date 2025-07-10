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
public class RemoveUser {

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final UserProfileRepository userProfileRepository;

    public UserResult handle(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("user not found");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("user not found"));

        UserProfile userProfile = userProfileRepository.findByUserId(user.getId())
                .orElse(null);

        String firstName = userProfile == null ? "" : userProfile.getFirstName();
        String lastName = userProfile == null ? "" : userProfile.getLastName();

        UserResult result = new UserResult(
                user.getId(),
                firstName,
                lastName,
                user.getUsername(),
                user.getEmail(),
                user.getStatus()
        );

        Set<UserRole> userRoles = userRoleRepository.findByUserId(id);
        userRoles.forEach(userRoleRepository::delete);

        if (userProfile != null) {
            userProfileRepository.delete(userProfile);
        }

        userRepository.deleteById(id);

        return result;
    }
}
