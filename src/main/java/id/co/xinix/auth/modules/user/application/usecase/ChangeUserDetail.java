package id.co.xinix.auth.modules.user.application.usecase;

import id.co.xinix.auth.modules.user.application.dto.UserCommand;
import id.co.xinix.auth.modules.user.application.dto.UserDTO;
import id.co.xinix.auth.modules.user.application.dto.UserUpdateCommand;
import id.co.xinix.auth.modules.user.application.dto.UserUpdatedResult;
import id.co.xinix.auth.modules.user.domain.service.UserService;
import id.co.xinix.spring.UseCase;
import id.co.xinix.spring.exception.NotFoundException;
import id.co.xinix.spring.modules.example.application.dto.ExampleCommand;
import id.co.xinix.spring.modules.example.application.dto.ExampleDTO;
import id.co.xinix.spring.modules.example.application.dto.ExampleUpdatedResult;
import id.co.xinix.spring.modules.example.domain.service.ExampleService;

@UseCase
public class ChangeUserDetail {

    private final UserService userService;

    public ChangeUserDetail(UserService userService) {
        this.userService = userService;
    }

    public UserUpdatedResult handle(UserUpdateCommand command) {
        UserDTO user = userService.findOne(command.getId())
                .map(exists -> {
                    exists.setUsername(command.getUsername());
                    exists.setEmail(command.getEmail());

                    if (command.getPassword() != null && !command.getPassword().isEmpty()) {
                        exists.setPassword(command.getPassword());
                    }

                    return exists;
                })
                .orElseThrow(() -> new NotFoundException("user not found"));

        UserDTO updatedUser = userService.update(user);

        return new UserUpdatedResult(
                updatedUser.getId(),
                updatedUser.getUsername(),
                updatedUser.getEmail()
        );
    }
}
