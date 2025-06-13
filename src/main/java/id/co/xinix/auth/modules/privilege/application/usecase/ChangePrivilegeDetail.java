package id.co.xinix.auth.modules.privilege.application.usecase;

import id.co.xinix.auth.UseCase;
import id.co.xinix.auth.exception.NotFoundException;
import id.co.xinix.auth.modules.privilege.application.dto.PrivilegeCommand;
import id.co.xinix.auth.modules.privilege.application.dto.PrivilegeUpdatedResult;
import id.co.xinix.auth.modules.privilege.domain.Privilege;
import id.co.xinix.auth.modules.privilege.domain.PrivilegeRepository;

@UseCase
public class ChangePrivilegeDetail {

    private final PrivilegeRepository privilegeRepository;

    public ChangePrivilegeDetail(PrivilegeRepository privilegeRepository) {
        this.privilegeRepository = privilegeRepository;
    }

    public PrivilegeUpdatedResult handle(PrivilegeCommand command) {
        Privilege privilege = getPrivilegeById(command.getId());
        updatedPrivilegeFromCommand(privilege, command);
        Privilege updated = privilegeRepository.save(privilege);
        return mapToUpdatedResult(updated);
    }

    private Privilege getPrivilegeById(Long id) {
        return privilegeRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("privilege not found"));
    }

    private void updatedPrivilegeFromCommand(Privilege privilege, PrivilegeCommand command) {
        privilege.setModule(command.getModule());
        privilege.setSubmodule(command.getModule());
        privilege.setOrdering(command.getOrdering());
        privilege.setAction(command.getAction());
        privilege.setMethod(command.getMethod());
        privilege.setUri(command.getUri());
    }

    private PrivilegeUpdatedResult mapToUpdatedResult(Privilege privilege) {
        return new PrivilegeUpdatedResult(
            privilege.getId(),
            privilege.getMethod(),
            privilege.getSubmodule(),
            privilege.getOrdering(),
            privilege.getAction(),
            privilege.getMethod(),
            privilege.getUri()
        );
    }
}
