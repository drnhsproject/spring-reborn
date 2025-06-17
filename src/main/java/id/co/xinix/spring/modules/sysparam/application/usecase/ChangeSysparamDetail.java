package id.co.xinix.spring.modules.sysparam.application.usecase;

import id.co.xinix.spring.UseCase;
import id.co.xinix.spring.exception.NotFoundException;
import id.co.xinix.spring.modules.sysparam.application.dto.SysparamCommand;
import id.co.xinix.spring.modules.sysparam.application.dto.SysparamUpdatedResult;
import id.co.xinix.spring.modules.sysparam.domain.Sysparam;
import id.co.xinix.spring.modules.sysparam.domain.SysparamRepository;
import lombok.AllArgsConstructor;

@UseCase
@AllArgsConstructor
public class ChangeSysparamDetail {

    private final SysparamRepository sysparamRepository;

    public SysparamUpdatedResult handle(SysparamCommand command) {
        Sysparam sysparam = getSysparamById(command.getId());
        updatedSysparamFromCommand(sysparam, command);
        Sysparam updated = sysparamRepository.save(sysparam);
        return mapToUpdatedResult(updated);
    }

    private Sysparam getSysparamById(Long id) {
        return sysparamRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("sysparam not found"));
    }

    private void updatedSysparamFromCommand(Sysparam sysparam, SysparamCommand command) {
        sysparam.setGroup(command.getGroup());
        sysparam.setKey(command.getKey());
        sysparam.setValue(command.getValue());
        sysparam.setLongValue(command.getLongValue());
    }

    private SysparamUpdatedResult mapToUpdatedResult(Sysparam sysparam) {
        return new SysparamUpdatedResult(
            sysparam.getId(),
            sysparam.getGroup(),
            sysparam.getKey(),
            sysparam.getValue(),
            sysparam.getLongValue()
        );
    }
}
