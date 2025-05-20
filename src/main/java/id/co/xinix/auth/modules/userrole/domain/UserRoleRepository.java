package id.co.xinix.auth.modules.userrole.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Set<UserRole> findByUserId (Long userId);
}
