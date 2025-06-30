package id.co.xinix.auth.modules.user.application.usecase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.xinix.auth.exception.DomainException;
import id.co.xinix.auth.modules.role.domain.Role;
import id.co.xinix.auth.modules.role.domain.RoleRepository;
import id.co.xinix.auth.modules.user.application.dto.UserCommand;
import id.co.xinix.auth.modules.user.application.dto.UserRegisteredResult;
import id.co.xinix.auth.modules.user.domain.UserRegisteredEvent;
import id.co.xinix.auth.modules.user.domain.User;
import id.co.xinix.auth.modules.user.domain.UserRepository;
import id.co.xinix.auth.UseCase;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@UseCase
@AllArgsConstructor
@Transactional
public class RegisterUser {

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final ApplicationEventPublisher eventPublisher;

    public UserRegisteredResult handle(UserCommand command) throws JsonProcessingException {
        String encodedPassword = passwordEncoder.encode(command.getPassword());

        List<Role> roles = roleRepository.findByCodeIn(command.getRole());

        User user = new User();
        user.setEmail(command.getEmail());
        user.setUsername(command.getUsername());
        user.setPassword(encodedPassword);

        String roleCodes = roles.stream().map(Role::getCode).collect(Collectors.joining(","));

        user.setRoleCode(roleCodes);

        validateUserExistence(user);

        String photoProfile = getStringPhotoProfile(command);

        User savedUser = userRepository.save(user);
        eventPublisher.publishEvent(new UserRegisteredEvent(
                this,
                roles,
                savedUser.getId(),
                command.getFirst_name(),
                command.getLast_name(),
                photoProfile
        ));

        return new UserRegisteredResult(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getUsername()
        );
    }

    private String getStringPhotoProfile(UserCommand command) throws JsonProcessingException {
        if (command.getPhoto() != null) {
            return new ObjectMapper().writeValueAsString(command.getPhoto());
        }
        return null;
    }


    private void validateUserExistence(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new DomainException("username already exists");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DomainException("email already exists");
        }
    }
}
