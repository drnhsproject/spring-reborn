package id.co.xinix.auth.modules.role.application.event;

import id.co.xinix.auth.modules.privilege.domain.Privilege;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
public class RoleCreatedEvent extends ApplicationEvent {
    private final String roleCode;

    private final List<Privilege> privileges;

    public RoleCreatedEvent(Object source, String roleCode, List<Privilege> privileges) {
        super(source);
        this.roleCode = roleCode;
        this.privileges = privileges;
    }
}
