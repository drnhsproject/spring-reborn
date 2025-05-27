package id.co.xinix.auth.modules.user.infrastructure.presistence;

import id.co.xinix.auth.modules.user.application.IUserMapper;
import id.co.xinix.auth.modules.user.application.dto.UserCommand;
import id.co.xinix.auth.modules.user.application.dto.UserDTO;
import id.co.xinix.auth.modules.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements IUserMapper {

    @Override
    public User toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }

        User entity = new User();
        entity.setId(dto.getId());
        entity.setUsername(dto.getUsername());
        entity.setEmail(dto.getEmail());
        entity.setStatus(dto.getStatus());

        return entity;
    }

    @Override
    public UserDTO toDTO(User entity) {
        if (entity == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setEmail(entity.getEmail());
        dto.setStatus(entity.getStatus());

        return dto;
    }

    @Override
    public UserDTO commandToDTO(UserCommand command) {
        if (command == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setUsername(command.getUsername());
        dto.setEmail(command.getEmail());
        dto.setStatus(1);

        return dto;
    }

    @Override
    public User commandToEntity(UserCommand command) {
        return null;
    }

    @Override
    public UserDTO commandAndEntityToDTO(UserCommand command, User entity) {
        return null;
    }

    @Override
    public User commandAndDtoToEntity(UserCommand command, UserDTO dto) {
        return null;
    }
}
