package id.co.xinix.auth.modules.role.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findByIdIn (List<String> ids);

    List<Role> findByCodeIn(List<String> codes);

    Optional<Role> findByCode(String code);

    Boolean existsByCode (String code);

    Boolean existsByName (String name);
}
