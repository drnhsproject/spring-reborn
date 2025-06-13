package id.co.xinix.auth.modules.user.application.usecase;

import id.co.xinix.auth.UseCase;
import id.co.xinix.auth.exception.NotFoundException;
import id.co.xinix.auth.modules.user.domain.UserRepository;

@UseCase
public class RemoveUser {

    private final UserRepository userRepository;

    public RemoveUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void handle(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("user not found");
        }

        userRepository.deleteById(id);
    }
}
