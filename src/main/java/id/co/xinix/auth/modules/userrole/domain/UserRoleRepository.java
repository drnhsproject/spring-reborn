package id.co.xinix.auth.modules.userrole.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
@RepositoryRestResource(exported = false)
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Set<UserRole> findByUserId(Long userId);
}
