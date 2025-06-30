package id.co.xinix.spring.modules.userprofile.application.listener;

import id.co.xinix.auth.modules.user.domain.UserUpdatedEvent;
import id.co.xinix.spring.modules.userprofile.application.usecase.UpdateUserProfileFromUserUpdatedEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
@Transactional
public class OnUserUpdatedUpdateUserProfile {

    private final UpdateUserProfileFromUserUpdatedEvent updateUserProfileFromUserUpdatedEvent;

    @EventListener
    public void handle(UserUpdatedEvent event) {
        Long userId = event.getUserId();
        String firstName = event.getFirstName();
        String lastName = event.getLastName();
        String photo = event.getPhoto();

        updateUserProfileFromUserUpdatedEvent.handle(userId, firstName, lastName, photo);
    }
}
