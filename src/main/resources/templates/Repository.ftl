package ${basePackage}.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ${entity.name}Repository extends JpaRepository<${entity.name}, Long> {
}
