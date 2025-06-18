package id.co.xinix.spring.modules.sysparam.application.usecase;

import id.co.xinix.auth.exception.NotFoundException;
import id.co.xinix.spring.UseCase;
import id.co.xinix.spring.modules.sysparam.application.dto.SysparamDetailResult;
import id.co.xinix.spring.modules.sysparam.domain.Sysparam;
import id.co.xinix.spring.modules.sysparam.domain.SysparamRepository;
import lombok.AllArgsConstructor;

@UseCase
@AllArgsConstructor
public class GetSysparamDetailById {

    private final SysparamRepository sysparamRepository;

    public SysparamDetailResult handle(Long id) {
        Sysparam sysparam = sysparamRepository.findById(id)
            .orElseThrow(() ->new NotFoundException("sysparam not found"));

        return mapTopDto(sysparam);
    }

    private SysparamDetailResult mapTopDto(Sysparam sysparam) {
        return new SysparamDetailResult(
            sysparam.getId(),
            sysparam.getGroup(),
            sysparam.getKey(),
            sysparam.getValue(),
            sysparam.getLongValue()
        );
    }
}
