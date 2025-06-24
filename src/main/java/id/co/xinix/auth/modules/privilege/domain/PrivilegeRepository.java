package id.co.xinix.auth.modules.privilege.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(exported = false)
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
}
