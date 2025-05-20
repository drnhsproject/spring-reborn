package id.co.xinix.auth.modules.privilege.application;

import id.co.xinix.auth.UseCase;
import id.co.xinix.auth.exception.DomainException;
import id.co.xinix.auth.modules.privilege.application.dto.PrivilegeCommand;
import id.co.xinix.auth.modules.privilege.application.dto.PrivilegeCreatedResult;
import id.co.xinix.auth.modules.privilege.domain.Privilege;
import id.co.xinix.auth.modules.privilege.domain.PrivilegeRepository;
import lombok.AllArgsConstructor;

@UseCase
@AllArgsConstructor
public class CreatePrivilege {

    private final PrivilegeRepository privilegeRepository;

    public PrivilegeCreatedResult handle(PrivilegeCommand command) {
        validateId(command.getId());

        Privilege privilege = new Privilege();
        privilege.setUri(command.getUri());
        privilege.setMethod(command.getMethod());
        privilege.setModule(command.getModule());
        privilege.setSubmodule(command.getSubModule());
        privilege.setAction(command.getAction());
        privilege.setMethod(command.getMethod());
        privilege.setOrdering(command.getOrdering());

        Privilege savedPrivilege = privilegeRepository.save(privilege);

        return new PrivilegeCreatedResult(
                savedPrivilege.getId(),
                savedPrivilege.getUri(),
                savedPrivilege.getModule(),
                savedPrivilege.getSubmodule(),
                savedPrivilege.getAction(),
                savedPrivilege.getMethod(),
                savedPrivilege.getOrdering()
        );
    }

    private void validateId(Long id) {
        if (id != null) {
            throw new DomainException("new privileges id exists");
        }
    }

}
