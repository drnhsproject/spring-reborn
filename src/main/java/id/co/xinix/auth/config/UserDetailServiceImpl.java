package id.co.xinix.auth.config;

import id.co.xinix.auth.modules.role.domain.Role;
import id.co.xinix.auth.modules.role.domain.RoleRepository;
import id.co.xinix.auth.modules.roleprivilege.domain.RolePrivilege;
import id.co.xinix.auth.modules.roleprivilege.domain.RolePrivilegeRepository;
import id.co.xinix.auth.modules.user.domain.User;
import id.co.xinix.auth.modules.user.domain.UserRepository;
import id.co.xinix.auth.modules.userrole.domain.UserRole;
import id.co.xinix.auth.modules.userrole.domain.UserRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final RoleRepository roleRepository;

    private final RolePrivilegeRepository rolePrivilegeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("username not found"));

        Set<UserRole> userRoles = userRoleRepository.findByUserId(user.getId());

        Set<Role> roles = userRoles.stream()
                .map(userRole -> roleRepository.findByCode(userRole.getRoleCode()))
                .flatMap(Optional::stream)
                .collect(Collectors.toSet());

        Set<RolePrivilege> rolePrivileges = roles
                .stream()
                .flatMap(role -> rolePrivilegeRepository.findByRoleCode(role.getCode()).stream())
                .collect(Collectors.toSet());

        Set<SimpleGrantedAuthority> authorities = rolePrivileges
                .stream()
                .map(privilege -> new SimpleGrantedAuthority(privilege.getUri()))
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
