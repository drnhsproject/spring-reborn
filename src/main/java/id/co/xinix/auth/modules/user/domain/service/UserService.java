package id.co.xinix.auth.modules.user.domain.service;

import id.co.xinix.auth.modules.user.application.dto.UserDTO;

import java.util.Optional;

public interface UserService {

    Optional<UserDTO> findOne(Long id);
}
