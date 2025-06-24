package id.co.xinix.auth.modules.role.application.usecase;

import id.co.xinix.auth.UseCase;
import id.co.xinix.auth.exception.DomainException;
import id.co.xinix.auth.modules.role.application.dto.RoleCommand;
import id.co.xinix.auth.modules.role.application.dto.RoleCreatedResult;
import id.co.xinix.auth.modules.role.domain.RoleCreatedEvent;
import id.co.xinix.auth.modules.role.domain.Role;
import id.co.xinix.auth.modules.role.domain.RoleRepository;
import id.co.xinix.auth.services.GenerateRandomCode;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@AllArgsConstructor
@Transactional
public class CreateRole {

    private final ApplicationEventPublisher eventPublisher;

    private final RoleRepository roleRepository;

    public RoleCreatedResult handle(RoleCommand command) {
        validateRoleId(command.getId());

        String prefix = "ROLE_";
        String generatedCode = new GenerateRandomCode().generate(prefix);

        validateUniqueNameAndCode(command.getName(), generatedCode);

        Role role = new Role();
        role.setCode(generatedCode);
        role.setName(command.getName());

        Role savedRole = roleRepository.save(role);
        eventPublisher.publishEvent(new RoleCreatedEvent(this, savedRole.getCode(), command.getPrivileges()));

        return new RoleCreatedResult(
                savedRole.getId(),
                savedRole.getName()
        );
    }

    private void validateRoleId(Long id) {
        if (id != null) {
            throw new DomainException("new role id exists");
        }
    }

    private void validateUniqueNameAndCode(String name, String code) {
        if (roleRepository.existsByName(name)) {
            throw new DomainException("role name already exists");
        }

        if (roleRepository.existsByCode(code)) {
            throw new DomainException("role code already exists");
        }
    }
}
