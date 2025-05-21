package id.co.xinix.auth.modules.user.application.usecase;

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

    public UserRegisteredResult handle(UserCommand command) {
        String encodedPassword = passwordEncoder.encode(command.getPassword());

        List<Role> roles = roleRepository.findByIdIn(command.getRoles());

        User user = new User();
        user.setEmail(command.getEmail());
        user.setUsername(command.getUsername());
        user.setPassword(encodedPassword);

        String roleCodes = roles.stream().map(Role::getCode).collect(Collectors.joining(","));

        user.setRoleCode(roleCodes);

        validateUserExistence(user);

        User savedUser = userRepository.save(user);
        eventPublisher.publishEvent(new UserRegisteredEvent(this, roles, savedUser.getId()));

        return new UserRegisteredResult(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getUsername()
        );
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
