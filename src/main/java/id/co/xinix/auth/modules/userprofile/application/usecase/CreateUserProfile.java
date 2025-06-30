package id.co.xinix.auth.modules.userprofile.application.usecase;

import id.co.xinix.auth.modules.userprofile.application.dto.UserProfileCommand;
import id.co.xinix.auth.modules.userprofile.application.dto.UserProfileCreatedResult;
import id.co.xinix.auth.modules.userprofile.domain.UserProfile;
import id.co.xinix.auth.modules.userprofile.domain.UserProfileRepository;
import id.co.xinix.spring.UseCase;
import lombok.AllArgsConstructor;

@UseCase
@AllArgsConstructor
public class CreateUserProfile {

    private final UserProfileRepository userProfileRepository;

    public UserProfileCreatedResult handle(UserProfileCommand command) {
        UserProfile userProfile = mapUserProfile(command);

        UserProfile savedUserProfile = userProfileRepository.save(userProfile);

        return new UserProfileCreatedResult(
            savedUserProfile.getId(),
            savedUserProfile.getCode(),
            savedUserProfile.getFirstName(),
            savedUserProfile.getLastName(),
            savedUserProfile.getIdentityNumber(),
            savedUserProfile.getPhoneNumber(),
            savedUserProfile.getPhoto(),
            savedUserProfile.getAddress(),
            savedUserProfile.getUserId()
        );
    }

    private UserProfile mapUserProfile(UserProfileCommand command) {
        UserProfile userProfile = new UserProfile();
        userProfile.setId(command.getId());
        userProfile.setCode(command.getCode());
        userProfile.setFirstName(command.getFirstName());
        userProfile.setLastName(command.getLastName());
        userProfile.setIdentityNumber(command.getIdentityNumber());
        userProfile.setPhoneNumber(command.getPhoneNumber());
        userProfile.setPhoto(command.getPhoto());
        userProfile.setAddress(command.getAddress());
        userProfile.setUserId(command.getUserId());
        return userProfile;
    }
}
