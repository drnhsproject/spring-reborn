package id.co.xinix.auth.modules.privilege.application.usecase;

import id.co.xinix.auth.UseCase;
import id.co.xinix.auth.modules.privilege.domain.Privilege;
import id.co.xinix.auth.modules.privilege.domain.PrivilegeRepository;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@UseCase
@AllArgsConstructor
public class GetPrivilegesFormatted {

    private final PrivilegeRepository privilegeRepository;

    public List<Map<String, Object>> fetchGroupedPrivileges() {
        List<Privilege> privileges = privilegeRepository.findByIsActiveTrue();

        Map<String, List<Privilege>> grouped = privileges.stream().collect(Collectors.groupingBy(Privilege::getSubmodule));

        return grouped
                .entrySet()
                .stream()
                .map(entry -> {
                    Map<String, Object> obj = new HashMap<>();
                    obj.put("name", entry.getKey());
                    obj.put("mapping", entry.getValue());
                    return obj;
                })
                .collect(Collectors.toList());
    }
}
