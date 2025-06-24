package id.co.xinix.auth.modules.role.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RepositoryRestResource(exported = false)
public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findByIdIn (List<String> ids);

    List<Role> findByCodeIn(List<String> codes);

    Optional<Role> findByCode(String code);

    Boolean existsByCode (String code);

    Boolean existsByName (String name);
}
