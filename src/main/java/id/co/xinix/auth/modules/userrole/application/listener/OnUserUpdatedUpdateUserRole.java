package id.co.xinix.auth.modules.userrole.application.listener;

import id.co.xinix.auth.modules.user.domain.UserUpdatedEvent;
import id.co.xinix.auth.modules.userrole.application.usecase.UpdateUserRoleFromUserUpdatedEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
@Transactional
public class OnUserUpdatedUpdateUserRole {

    private final UpdateUserRoleFromUserUpdatedEvent updateUserRoleFromUserUpdatedEvent;

    @EventListener
    public void handle(UserUpdatedEvent event) {
        updateUserRoleFromUserUpdatedEvent.handle(event);
    }
}
