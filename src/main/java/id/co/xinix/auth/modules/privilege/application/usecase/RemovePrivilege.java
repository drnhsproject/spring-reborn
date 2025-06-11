package id.co.xinix.auth.modules.privilege.application.usecase;

import id.co.xinix.auth.UseCase;
import id.co.xinix.auth.modules.privilege.domain.PrivilegeRepository;

import java.util.NoSuchElementException;

@UseCase
public class RemovePrivilege {

    private final PrivilegeRepository privilegeRepository;

    public RemovePrivilege(PrivilegeRepository privilegeRepository) {
        this.privilegeRepository = privilegeRepository;
    }

    public void handle(Long id) {
        if (!privilegeRepository.existsById(id)) {
            throw new NoSuchElementException("Privilege not found with id: " + id);
        }

        privilegeRepository.deleteById(id);
    }
}
