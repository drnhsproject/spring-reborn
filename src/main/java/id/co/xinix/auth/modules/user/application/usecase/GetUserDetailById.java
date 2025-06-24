package id.co.xinix.auth.modules.user.application.usecase;

import id.co.xinix.auth.UseCase;
import id.co.xinix.auth.exception.NotFoundException;
import id.co.xinix.auth.modules.user.application.dto.UserDetailResult;
import id.co.xinix.auth.modules.user.domain.User;
import id.co.xinix.auth.modules.user.domain.UserRepository;
import id.co.xinix.auth.modules.userrole.domain.UserRole;
import id.co.xinix.auth.modules.userrole.domain.UserRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@UseCase
@AllArgsConstructor
public class GetUserDetailById {

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    public UserDetailResult handle(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("user not found"));

        Set<UserRole> userRoles = userRoleRepository.findByUserId(user.getId());

        if (userRoles.isEmpty()) {
            throw new NotFoundException("user roles not found");
        }

        List<String> roleIds = userRoles
                .stream()
                .map(UserRole::getRoleCode)
                .toList();

        return mapToDto(user, roleIds);
    }

    private UserDetailResult mapToDto(User user, List<String> roleIds) {
        return new UserDetailResult(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                roleIds,
                user.getStatus()
        );
    }
}
