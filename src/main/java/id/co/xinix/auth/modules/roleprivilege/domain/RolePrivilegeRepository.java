package id.co.xinix.auth.modules.roleprivilege.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Repository
@RepositoryRestResource(exported = false)
public interface RolePrivilegeRepository extends JpaRepository<RolePrivilege, Long> {
    List<RolePrivilege> findByRoleCodeIn (Set<String> roleCode);
    List<RolePrivilege> findByRoleCode (String roleCode);

    @Transactional
    void deleteByRoleCode(String roleCode);
}
