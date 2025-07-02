package id.co.xinix.spring.modules.sysparam.application.usecase;

import id.co.xinix.auth.exception.NotFoundException;
import id.co.xinix.spring.UseCase;
import id.co.xinix.spring.modules.sysparam.domain.Sysparam;
import id.co.xinix.spring.modules.sysparam.domain.SysparamRepository;
import lombok.AllArgsConstructor;

@UseCase
@AllArgsConstructor
public class RestoreSysparam {

    private final SysparamRepository sysparamRepository;

    public void handle(Long id) {
        Sysparam sysparam = sysparamRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("sysparam not found"));

        sysparam.setIsActive(true);
        sysparam.setStatus(1);

        sysparamRepository.save(sysparam);
    }
}
