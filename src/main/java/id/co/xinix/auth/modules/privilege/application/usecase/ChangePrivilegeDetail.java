package id.co.xinix.auth.modules.privilege.application.usecase;

import id.co.xinix.auth.UseCase;
import id.co.xinix.auth.modules.privilege.application.dto.PrivilegeCommand;
import id.co.xinix.auth.modules.privilege.application.dto.PrivilegeUpdatedResult;
import id.co.xinix.auth.modules.privilege.domain.Privilege;
import id.co.xinix.auth.modules.privilege.domain.PrivilegeRepository;

import java.util.NoSuchElementException;

@UseCase
public class ChangePrivilegeDetail {

    private final PrivilegeRepository privilegeRepository;

    public ChangePrivilegeDetail(PrivilegeRepository privilegeRepository) {
        this.privilegeRepository = privilegeRepository;
    }

    public PrivilegeUpdatedResult handle(PrivilegeCommand command) {
        Privilege privilege = getPrivilegeById(command.getId());
        updatePrivilegeFromCommand(privilege, command);
        Privilege updated = privilegeRepository.save(privilege);
        return mapToUpdatedResult(updated);
    }

    private Privilege getPrivilegeById(Long id) {
        return privilegeRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Privilege not found with id: " + id));
    }

    private void updatePrivilegeFromCommand(Privilege privilege, PrivilegeCommand command) {
        privilege.setUri(command.getUri());
        privilege.setModule(command.getModule());
        privilege.setSubmodule(command.getSubModule());
        privilege.setAction(command.getAction());
        privilege.setMethod(command.getMethod());
        privilege.setOrdering(command.getOrdering());
    }

    private PrivilegeUpdatedResult mapToUpdatedResult(Privilege privilege) {
        return new PrivilegeUpdatedResult(
            privilege.getId(),
            privilege.getModule(),
            privilege.getSubmodule(),
            privilege.getOrdering(),
            privilege.getAction(),
            privilege.getMethod(),
            privilege.getUri()
        );
    }
}
