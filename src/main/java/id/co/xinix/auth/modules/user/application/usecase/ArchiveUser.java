package id.co.xinix.auth.modules.user.application.usecase;

import id.co.xinix.auth.UseCase;
import id.co.xinix.auth.exception.NotFoundException;
import id.co.xinix.auth.modules.user.domain.User;
import id.co.xinix.auth.modules.user.domain.UserRepository;

@UseCase
public class ArchiveUser {

    private final UserRepository userRepository;

    public ArchiveUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void handle(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("user not found"));

        user.setIsActive(false);
        user.setStatus(0);

        userRepository.save(user);
    }
}
