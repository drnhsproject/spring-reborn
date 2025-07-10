package id.co.xinix.spring.modules.sysparam.application.usecase;

import id.co.xinix.auth.exception.NotFoundException;
import id.co.xinix.spring.UseCase;
import id.co.xinix.spring.modules.sysparam.application.dto.SysparamsResult;
import id.co.xinix.spring.modules.sysparam.domain.Sysparam;
import id.co.xinix.spring.modules.sysparam.domain.SysparamRepository;
import lombok.AllArgsConstructor;

@UseCase
@AllArgsConstructor
public class RemoveSysparam {

    private final SysparamRepository sysparamRepository;

    public SysparamsResult handle(Long id) {
        if (!sysparamRepository.existsById(id)) {
            throw new NotFoundException("sysparam not found");
        }

        Sysparam sysparam = sysparamRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("sysparam not found"));

        SysparamsResult result = new SysparamsResult(
            sysparam.getId(),
            sysparam.getGroup(),
            sysparam.getKey(),
            sysparam.getValue(),
            sysparam.getLongValue(),
            sysparam.getStatus()
        );

        sysparamRepository.deleteById(id);

        return result;
    }
}
