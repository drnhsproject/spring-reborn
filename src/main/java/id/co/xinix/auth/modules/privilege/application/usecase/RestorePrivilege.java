package id.co.xinix.auth.modules.privilege.application.usecase;

import id.co.xinix.auth.UseCase;
import id.co.xinix.auth.exception.NotFoundException;
import id.co.xinix.auth.modules.privilege.domain.Privilege;
import id.co.xinix.auth.modules.privilege.domain.PrivilegeRepository;
import lombok.AllArgsConstructor;

@UseCase
@AllArgsConstructor
public class RestorePrivilege {

    private final PrivilegeRepository privilegeRepository;

    public void handle(Long id) {
        Privilege privilege = privilegeRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("privilege not found"));

        privilege.setIsActive(true);
        privilege.setStatus(1);

        privilegeRepository.save(privilege);
    }
}
