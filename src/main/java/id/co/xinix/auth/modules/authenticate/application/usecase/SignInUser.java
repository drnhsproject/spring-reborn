package id.co.xinix.auth.modules.authenticate.application.usecase;

import id.co.xinix.auth.UseCase;
import id.co.xinix.auth.exception.BadRequestException;
import id.co.xinix.auth.exception.DomainException;
import id.co.xinix.auth.exception.NotFoundException;
import id.co.xinix.auth.exception.UnauthorizedException;
import id.co.xinix.auth.modules.authenticate.application.dto.SignInCommand;
import id.co.xinix.auth.modules.authenticate.application.dto.SignInResult;
import id.co.xinix.auth.modules.authenticate.domain.*;
import id.co.xinix.auth.modules.role.domain.Role;
import id.co.xinix.auth.modules.role.domain.RoleRepository;
import id.co.xinix.auth.modules.roleprivilege.domain.RolePrivilege;
import id.co.xinix.auth.modules.roleprivilege.domain.RolePrivilegeRepository;
import id.co.xinix.auth.modules.user.domain.UserRepository;
import id.co.xinix.auth.modules.userprofile.domain.UserProfile;
import id.co.xinix.auth.modules.userprofile.domain.UserProfileRepository;
import id.co.xinix.auth.modules.userrole.domain.UserRole;
import id.co.xinix.auth.modules.userrole.domain.UserRoleRepository;
import id.co.xinix.auth.security.jwt.TokenProvider;
import id.co.xinix.spring.services.GenerateRandomCode;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    private final UserProfileRepository userProfileRepository;

    public SignInResult handle(SignInCommand command) {
        validationIsBlankUsernameAndPassword(command);

        var user = userRepository.findByUsername(command.getUsername())
                .orElseThrow(() -> new UnauthorizedException("username or password is incorrect"));

        if (!passwordEncoder.matches(command.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("username or password is incorrect");
        }

        UserProfile userProfile = userProfileRepository
                .findByUserId(user.getId())
                .orElseGet(() -> {
                    UserProfile newProfile = new UserProfile();
                    newProfile.setUserId(user.getId());
                    newProfile.setCode(generateCode());
                    newProfile.setFirstName(user.getUsername());

                    return userProfileRepository.save(newProfile);
                });

        List<String> rolesUserDetail = List.of(user.getRoleCode());

        Set<UserRole> userRolesForUserDetail = userRoleRepository.findByUserId(user.getId());

        List<UserRoleSignIn> userRoleSignIns = userRolesForUserDetail.stream()
                .map(userRole -> {
                    Role role = roleRepository.findByCode(userRole.getRoleCode())
                            .orElseThrow(() -> new NotFoundException("role not found for code: " + userRole.getRoleCode()));

                    RoleSignIn roleSignIn = new RoleSignIn(
                            role.getId(),
                            role.getCode(),
                            role.getName(),
                            role.getStatus()
                    );

                    return new UserRoleSignIn(
                            userRole.getId(),
                            userRole.getUserId(),
                            roleSignIn,
                            userRole.getStatus()
                    );
                })
                .toList();

        UserDetail userDetail = new UserDetail(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getStatus(),
                userProfile.getFirstName(),
                userProfile.getLastName(),
                userProfile.getPhoto(),
                rolesUserDetail,
                userRoleSignIns
        );

        Authentication authentication = authenticateUser(command);
        String jwt = tokenProvider.generateToken(authentication, command.getRememberMe(), userDetail.username());

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
        rolePrivilegeDetail.setRole(dataUserRoleDetail);
        rolePrivilegeDetail.setPrivileges(dataPrivilegeDetail);

        SignInResult signInResult = new SignInResult();
        signInResult.setUser(userDetail);
        signInResult.setRole(rolePrivilegeDetail);
        signInResult.setAccessToken(jwt);

        return signInResult;
    }

    private String generateCode() {
        return new GenerateRandomCode().generate("USR_");
    }

    private void validationIsBlankUsernameAndPassword(SignInCommand command) {
        if (isBlank(command.getUsername()) || isBlank(command.getPassword())) {
            throw new DomainException("username or password can't be null");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private Authentication authenticateUser(SignInCommand command) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                command.getUsername(),
                command.getPassword()
        );

        return authenticationManager.authenticate(authenticationToken);
    }
}
