package id.co.xinix.spring.modules.userprofile.application.usecase;

import id.co.xinix.spring.UseCase;
import id.co.xinix.spring.modules.userprofile.application.dto.*;
import id.co.xinix.spring.modules.userprofile.domain.*;
import lombok.AllArgsConstructor;

@UseCase
@AllArgsConstructor
public class CreateUserProfile {

    private final UserProfileRepository userProfileRepository;

    public UserProfileCreatedResult handle(UserProfileCommand command) {
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
}
