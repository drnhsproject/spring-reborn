package id.co.xinix.auth.modules.privilege.application.usecase;

import id.co.xinix.auth.UseCase;
import id.co.xinix.auth.exception.NotFoundException;
import id.co.xinix.auth.modules.privilege.application.dto.PrivilegeDetailResult;
import id.co.xinix.auth.modules.privilege.domain.Privilege;
import id.co.xinix.auth.modules.privilege.domain.PrivilegeRepository;

@UseCase
public class GetPrivilegeDetailById {

    private final PrivilegeRepository privilegeRepository;

    public GetPrivilegeDetailById(PrivilegeRepository privilegeRepository) {
        this.privilegeRepository = privilegeRepository;
    }

    public PrivilegeDetailResult handle(Long id) {
        Privilege privilege = privilegeRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("privilege not found"));

        return mapToDto(privilege);
    }

    private PrivilegeDetailResult mapToDto(Privilege privilege) {
        return new PrivilegeDetailResult(
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
