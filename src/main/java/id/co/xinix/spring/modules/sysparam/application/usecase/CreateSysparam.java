package id.co.xinix.spring.modules.sysparam.application.usecase;

import id.co.xinix.spring.UseCase;
import id.co.xinix.spring.modules.sysparam.application.dto.*;
import id.co.xinix.spring.modules.sysparam.domain.*;
import lombok.AllArgsConstructor;

@UseCase
@AllArgsConstructor
public class CreateSysparam {

    private final SysparamRepository sysparamRepository;

    public SysparamCreatedResult handle(SysparamCommand command) {
        Sysparam sysparam = new Sysparam();
        sysparam.setId(command.getId());
        sysparam.setGroup(command.getGroup());
        sysparam.setKey(command.getKey());
        sysparam.setValue(command.getValue());
        sysparam.setLongValue(command.getLongValue());

        Sysparam savedSysparam = sysparamRepository.save(sysparam);

        return new SysparamCreatedResult(
            savedSysparam.getId(),
            savedSysparam.getGroup(),
            savedSysparam.getKey(),
            savedSysparam.getValue(),
            savedSysparam.getLongValue()
        );
    }
}
