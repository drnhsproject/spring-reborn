package id.co.xinix.spring.modules.sysparam.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(exported = false)
public interface SysparamRepository extends JpaRepository<Sysparam, Long> {
}
