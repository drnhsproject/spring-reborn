package id.co.xinix.auth.modules.user.application.usecase;

import id.co.xinix.auth.UseCase;
import id.co.xinix.auth.exception.NotFoundException;
import id.co.xinix.auth.modules.user.application.dto.UserDetailResult;
import id.co.xinix.auth.modules.user.domain.User;
import id.co.xinix.auth.modules.user.domain.UserRepository;

@UseCase
public class GetUserDetailById {

    private final UserRepository userRepository;

    public GetUserDetailById(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetailResult handle(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("user not found"));

        return mapToDto(user);
    }

    private UserDetailResult mapToDto(User user) {
        return new UserDetailResult(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }
}
