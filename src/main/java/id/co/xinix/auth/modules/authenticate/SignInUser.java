package id.co.xinix.auth.modules.authenticate;

import id.co.xinix.auth.UseCase;
import id.co.xinix.auth.exception.BadRequestException;
import id.co.xinix.auth.exception.UnauthorizedException;
import id.co.xinix.auth.modules.role.domain.Role;
import id.co.xinix.auth.modules.role.domain.RoleRepository;
import id.co.xinix.auth.modules.roleprivilege.domain.RolePrivilege;
import id.co.xinix.auth.modules.roleprivilege.domain.RolePrivilegeRepository;
import id.co.xinix.auth.modules.user.domain.UserRepository;
import id.co.xinix.auth.modules.userrole.domain.UserRole;
import id.co.xinix.auth.modules.userrole.domain.UserRoleRepository;
import id.co.xinix.auth.security.jwt.TokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@UseCase
@AllArgsConstructor
public class SignInUser {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final RoleRepository roleRepository;

    private final RolePrivilegeRepository rolePrivilegeRepository;

    private final AuthenticationManager authenticationManager;

    private final TokenProvider tokenProvider;

    public SignInResult handle(SignInCommand command) {
        var user = userRepository.findByUsername(command.getUsername())
                .orElseThrow(() -> new UnauthorizedException("username or password is incorrect"));

        if (!passwordEncoder.matches(command.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("username or password is incorrect");
        }

        UserDetail userDetail = new UserDetail(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getStatus()
        );

        Authentication authentication = authenticateUser(command);
        String jwt = tokenProvider.generateToken(authentication, command.getRememberMe(), userDetail);

        Set<UserRole> userRoles = userRoleRepository.findByUserId(userDetail.id());

        if (userRoles.isEmpty()) {
            throw new BadRequestException("no roles found for user");
        }

        Set<String> userRoleCode = userRoles.stream().map(UserRole::getRoleCode)
                .collect(Collectors.toSet());

        List<Role> roles = roleRepository.findByCodeIn(new ArrayList<>(userRoleCode));
        List<RolePrivilege> rolePrivileges = rolePrivilegeRepository.findByRoleCodeIn(userRoleCode);

        List<RoleDetail> dataUserRoleDetail = roles.stream()
                .map(role -> new RoleDetail(
                        role.getCode(),
                        role.getName()
                ))
                .toList();

        List<PrivilegeDetail> dataPrivilegeDetail = rolePrivileges.stream()
                .map(rolePrivilege -> new PrivilegeDetail(
                        rolePrivilege.getUri(),
                        rolePrivilege.getMethod()
                ))
                .toList();

        RolePrivilegeDetail rolePrivilegeDetail = new RolePrivilegeDetail();
        rolePrivilegeDetail.setRoles(dataUserRoleDetail);
        rolePrivilegeDetail.setPrivileges(dataPrivilegeDetail);

        SignInResult signInResult = new SignInResult();
        signInResult.setUser(userDetail);
        signInResult.setRole(rolePrivilegeDetail);
        signInResult.setAccessToken(jwt);

        return signInResult;
    }

    private Authentication authenticateUser(SignInCommand command) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                command.getUsername(),
                command.getPassword()
        );

        return authenticationManager.authenticate(authenticationToken);
    }
}
