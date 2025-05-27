package id.co.xinix.auth.modules.user.application.usecase;

import id.co.xinix.auth.UseCase;
import id.co.xinix.auth.exception.NotFoundException;
import id.co.xinix.auth.modules.user.application.dto.UserDetailResult;
import id.co.xinix.auth.modules.user.domain.service.UserService;

@UseCase("getUserDetail")
public class GetUserDetailById {

    private final UserService userService;

    public GetUserDetailById(UserService userService) {
        this.userService = userService;
    }

    public UserDetailResult handle(Long id) {
        return userService.findOne(id)
            .map(exists -> new UserDetailResult(
                exists.getId(),
                exists.getUsername(),
                exists.getEmail()
            ))
            .orElseThrow(() -> new NotFoundException("user not found"));
    }
}
