package id.co.xinix.auth.modules.user.application.usecase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.xinix.auth.UseCase;
import id.co.xinix.auth.exception.NotFoundException;
import id.co.xinix.auth.modules.role.domain.Role;
import id.co.xinix.auth.modules.role.domain.RoleRepository;
import id.co.xinix.auth.modules.user.application.dto.UserCommand;
import id.co.xinix.auth.modules.user.application.dto.UserUpdateCommand;
import id.co.xinix.auth.modules.user.application.dto.UserUpdatedResult;
import id.co.xinix.auth.modules.user.domain.User;
import id.co.xinix.auth.modules.user.domain.UserRepository;
import id.co.xinix.auth.modules.user.domain.UserUpdatedEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.stream.Collectors;

@UseCase
@AllArgsConstructor
public class ChangeUserDetail {

    private final UserRepository userRepository;

    private final ApplicationEventPublisher eventPublisher;

    private final RoleRepository roleRepository;

    public UserUpdatedResult handle(Long id, UserUpdateCommand command) throws JsonProcessingException {
        List<Role> roles = roleRepository.findByCodeIn(command.getRole());
        User user = getUserById(id);
        updatedUserFromCommand(user, command, roles);
        String photoProfile = getStringPhotoProfile(command);

        User updated = userRepository.save(user);

        eventPublisher.publishEvent(new UserUpdatedEvent(
                this,
                roles ,updated.getId(),
                command.getFirst_name(),
                command.getLast_name(),
                photoProfile
        ));

        return mapToUpdatedResult(updated);
    }

    private String getStringPhotoProfile(UserUpdateCommand command) throws JsonProcessingException {
        if (command.getPhoto() != null) {
            return new ObjectMapper().writeValueAsString(command.getPhoto());
        }
        return null;
    }

    private User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("user not found"));
    }

    private void updatedUserFromCommand(User user, UserUpdateCommand command, List<Role> roles) {
        String roleCodes = roles.stream().map(Role::getCode).collect(Collectors.joining(","));

        user.setUsername(command.getUsername());
        user.setEmail(command.getEmail());
        user.setRoleCode(roleCodes);
    }

    private UserUpdatedResult mapToUpdatedResult(User user) {
        return new UserUpdatedResult(
            user.getId(),
            user.getUsername(),
            user.getEmail()
        );
    }
}
