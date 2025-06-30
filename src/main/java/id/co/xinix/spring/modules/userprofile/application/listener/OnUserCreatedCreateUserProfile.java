package id.co.xinix.spring.modules.userprofile.application.listener;

import id.co.xinix.auth.modules.user.domain.UserRegisteredEvent;
import id.co.xinix.spring.modules.userprofile.application.usecase.CreateUserProfileFromUserRegisteredEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
@Transactional
public class OnUserCreatedCreateUserProfile {

    private final CreateUserProfileFromUserRegisteredEvent createUserProfileFromUserRegisteredEvent;

    @EventListener
    public void handle(UserRegisteredEvent event) {
        Long userId = event.getUserId();
        String firstName = event.getFirstName();
        String lastName = event.getLastName();
        String photo = event.getPhoto();

        createUserProfileFromUserRegisteredEvent.handle(userId, firstName, lastName, photo);
    }
}
