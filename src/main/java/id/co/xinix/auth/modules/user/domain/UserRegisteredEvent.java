package id.co.xinix.auth.modules.user.domain;

import id.co.xinix.auth.modules.role.domain.Role;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
public class UserRegisteredEvent extends ApplicationEvent {
    private final List<Role> roles;

    private final Long userId;

    private final String firstName;

    private final String lastName;

    private final String photo;

    public UserRegisteredEvent(
            Object source,
            List<Role> roles,
            Long userId,
            String firstName,
            String lastName, String photo
    ) {
        super(source);
        this.roles = roles;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.photo = photo;
    }
}
