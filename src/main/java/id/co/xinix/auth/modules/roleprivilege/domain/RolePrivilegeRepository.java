package id.co.xinix.auth.modules.roleprivilege.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface RolePrivilegeRepository extends JpaRepository<RolePrivilege, Long> {
    List<RolePrivilege> findByRoleCodeIn (Set<String> roleCode);
    List<RolePrivilege> findByRoleCode (String roleCode);

    @Transactional
    void deleteByRoleCode(String roleCode);
}
