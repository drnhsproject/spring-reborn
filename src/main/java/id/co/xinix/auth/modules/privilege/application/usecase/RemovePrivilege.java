package id.co.xinix.auth.modules.privilege.application.usecase;

import id.co.xinix.auth.UseCase;
import id.co.xinix.auth.exception.NotFoundException;
import id.co.xinix.auth.modules.privilege.application.dto.PrivilegeResult;
import id.co.xinix.auth.modules.privilege.domain.Privilege;
import id.co.xinix.auth.modules.privilege.domain.PrivilegeRepository;

@UseCase
public class RemovePrivilege {

    private final PrivilegeRepository privilegeRepository;

    public RemovePrivilege(PrivilegeRepository privilegeRepository) {
        this.privilegeRepository = privilegeRepository;
    }

    public PrivilegeResult handle(Long id) {
        if (!privilegeRepository.existsById(id)) {
            throw new NotFoundException("privilege not found");
        }

        Privilege privilege = privilegeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("privilege not found"));

        PrivilegeResult result = new PrivilegeResult(
                privilege.getId(),
                privilege.getUri(),
                privilege.getModule(),
                privilege.getSubmodule(),
                privilege.getAction(),
                privilege.getMethod(),
                privilege.getOrdering(),
                privilege.getStatus()
        );

        privilegeRepository.deleteById(id);

        return result;
    }
}
