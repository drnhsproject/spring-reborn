package id.co.xinix.auth.modules.user.domain.service;

import id.co.xinix.auth.modules.user.application.IUserMapper;
import id.co.xinix.auth.modules.user.application.dto.UserDTO;
import id.co.xinix.auth.modules.user.domain.User;
import id.co.xinix.auth.modules.user.domain.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private final IUserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, IUserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        User existing = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        existing.setUsername(userDTO.getUsername());
        existing.setEmail(userDTO.getEmail());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            existing.setPassword(userDTO.getPassword());
        }

        User saved = userRepository.save(existing);
        return userMapper.toDTO(saved);
    }

    @Override
    public Optional<UserDTO> findOne(Long id) {
        return userRepository.findById(id).map(userMapper::toDTO);
    }
}
