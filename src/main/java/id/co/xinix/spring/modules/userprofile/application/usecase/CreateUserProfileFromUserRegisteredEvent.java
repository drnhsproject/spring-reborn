package id.co.xinix.spring.modules.userprofile.application.usecase;

import id.co.xinix.spring.UseCase;
import id.co.xinix.spring.exception.NotFoundException;
import id.co.xinix.spring.modules.userprofile.domain.UserProfile;
import id.co.xinix.spring.modules.userprofile.domain.UserProfileRepository;
import id.co.xinix.spring.services.GenerateRandomCode;
import lombok.AllArgsConstructor;

@UseCase
@AllArgsConstructor
public class CreateUserProfileFromUserRegisteredEvent {

    private final UserProfileRepository userProfileRepository;

    public void handle(Long userId, String firstName, String lastName, String photo) {
        UserProfile userProfile = userProfileRepository.findByUserId(userId)
                .orElseGet(() -> {
                    UserProfile profile = new UserProfile();
                    profile.setUserId(userId);
                    profile.setCode(generateCode());
                    return profile;
                });

        userProfile.setFirstName(firstName);
        userProfile.setLastName(lastName);
        userProfile.setPhoto(photo);

        userProfileRepository.save(userProfile);
    }

    private String generateCode() {
        return new GenerateRandomCode().generate("USR_");
    }
}
