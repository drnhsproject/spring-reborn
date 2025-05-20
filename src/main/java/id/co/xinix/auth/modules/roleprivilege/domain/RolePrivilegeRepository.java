package id.co.xinix.auth.modules.roleprivilege.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface RolePrivilegeRepository extends JpaRepository<RolePrivilege, Long> {
    List<RolePrivilege> findByRoleCodeIn (Set<String> roleCode);
    List<RolePrivilege> findByRoleCode (String roleCode);
}
