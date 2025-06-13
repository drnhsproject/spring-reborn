package id.co.xinix.auth.modules.user.application.usecase;

import id.co.xinix.auth.UseCase;
import id.co.xinix.auth.exception.NotFoundException;
import id.co.xinix.auth.modules.user.application.dto.UserUpdateCommand;
import id.co.xinix.auth.modules.user.application.dto.UserUpdatedResult;
import id.co.xinix.auth.modules.user.domain.User;
import id.co.xinix.auth.modules.user.domain.UserRepository;

@UseCase
public class ChangeUserDetail {

    private final UserRepository userRepository;

    public ChangeUserDetail(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserUpdatedResult handle(UserUpdateCommand command) {
        User user = getUserById(command.getId());
        updatedUserFromCommand(user, command);
        User updated = userRepository.save(user);
        return mapToUpdatedResult(updated);
    }

    private User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("user not found"));
    }

    private void updatedUserFromCommand(User user, UserUpdateCommand command) {
        user.setUsername(command.getUsername());
        user.setEmail(command.getEmail());
    }

    private UserUpdatedResult mapToUpdatedResult(User user) {
        return new UserUpdatedResult(
            user.getId(),
            user.getUsername(),
            user.getEmail()
        );
    }
}
