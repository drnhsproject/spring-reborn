package id.co.xinix.spring.modules.sysparam.application.usecase;

import id.co.xinix.auth.exception.NotFoundException;
import id.co.xinix.spring.UseCase;
import id.co.xinix.spring.modules.sysparam.domain.SysparamRepository;
import lombok.AllArgsConstructor;

@UseCase
@AllArgsConstructor
public class RemoveSysparam {

    private final SysparamRepository sysparamRepository;

    public void handle(Long id) {
        if (!sysparamRepository.existsById(id)) {
            throw new NotFoundException("sysparam not found");
        }

        sysparamRepository.deleteById(id);
    }
}
