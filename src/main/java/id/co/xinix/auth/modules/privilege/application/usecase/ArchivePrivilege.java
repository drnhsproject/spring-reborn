package id.co.xinix.auth.modules.privilege.application.usecase;

import id.co.xinix.auth.UseCase;
import id.co.xinix.auth.exception.NotFoundException;
import id.co.xinix.auth.modules.privilege.domain.Privilege;
import id.co.xinix.auth.modules.privilege.domain.PrivilegeRepository;

@UseCase
public class ArchivePrivilege {

    private final PrivilegeRepository privilegeRepository;

    public ArchivePrivilege(PrivilegeRepository privilegeRepository) {
        this.privilegeRepository = privilegeRepository;
    }

    public void handle(Long id) {
        Privilege privilege = privilegeRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("privilege not found"));

        privilege.setIsActive(false);
        privilege.setStatus(0);

        privilegeRepository.save(privilege);
    }
}
