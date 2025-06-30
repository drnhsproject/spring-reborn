package id.co.xinix.auth.modules.user.application.usecase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.xinix.auth.UseCase;
import id.co.xinix.auth.exception.NotFoundException;
import id.co.xinix.auth.modules.user.application.dto.PhotoProfile;
import id.co.xinix.auth.modules.user.application.dto.UserDetailResult;
import id.co.xinix.auth.modules.user.domain.User;
import id.co.xinix.auth.modules.user.domain.UserRepository;
import id.co.xinix.auth.modules.userrole.domain.UserRole;
import id.co.xinix.auth.modules.userrole.domain.UserRoleRepository;
import id.co.xinix.auth.modules.userprofile.domain.UserProfile;
import id.co.xinix.auth.modules.userprofile.domain.UserProfileRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Set;

@UseCase
@AllArgsConstructor
public class GetUserDetailById {

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final UserProfileRepository userProfileRepository;

    public UserDetailResult handle(Long id) throws JsonProcessingException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("user not found"));

        Set<UserRole> userRoles = userRoleRepository.findByUserId(user.getId());

        UserProfile userProfile = userProfileRepository.findByUserId(user.getId())
            .orElse(null);

        if (userRoles.isEmpty()) {
            throw new NotFoundException("user roles not found");
        }

        List<String> roleIds = userRoles
                .stream()
                .map(UserRole::getRoleCode)
                .toList();

        return mapToDto(user, roleIds, userProfile);
    }

    private UserDetailResult mapToDto(User user, List<String> roleIds, UserProfile userProfile) throws JsonProcessingException {
        String firstName = userProfile != null ? userProfile.getFirstName() : "";
        String lastName = userProfile != null ? userProfile.getLastName() : "";
        String photo = userProfile != null ? userProfile.getPhoto() : null;

        ObjectMapper objectMapper = new ObjectMapper();

        PhotoProfile photoProfile = null;
        if (photo != null && !photo.isBlank()) {
            photoProfile = objectMapper.readValue(photo, PhotoProfile.class);
        }

        return new UserDetailResult(
                user.getId(),
                firstName,
                lastName,
                user.getUsername(),
                user.getEmail(),
                roleIds,
                user.getStatus(),
                photoProfile
        );
    }
}
