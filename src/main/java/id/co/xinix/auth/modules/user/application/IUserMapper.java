package id.co.xinix.auth.modules.user.application;

import id.co.xinix.auth.modules.user.application.dto.UserCommand;
import id.co.xinix.auth.modules.user.application.dto.UserDTO;
import id.co.xinix.auth.modules.user.domain.User;
import id.co.xinix.spring.services.IDomainMapper;

public interface IUserMapper extends IDomainMapper<UserDTO, User, UserCommand> {
}
