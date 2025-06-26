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

    public void handle(Long userId, String firstName, String lastName) {
        UserProfile userProfile = userProfileRepository.findByUserId(userId)
            .orElseThrow(() -> new NotFoundException("user profile not found"));

        userProfile.setFirstName(firstName);
        userProfile.setLastName(lastName);

        userProfileRepository.save(userProfile);
    }

    private String generateCode() {
        return new GenerateRandomCode().generate("USR_");
    }
}
