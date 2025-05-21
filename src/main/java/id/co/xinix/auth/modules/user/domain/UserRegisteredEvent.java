package id.co.xinix.auth.modules.user.domain;

import id.co.xinix.auth.modules.role.domain.Role;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
public class UserRegisteredEvent extends ApplicationEvent {
    private final List<Role> roles;

    private final Long userId;

    public UserRegisteredEvent(Object source, List<Role> roles, Long userId) {
        super(source);
        this.roles = roles;
        this.userId = userId;
    }
}
