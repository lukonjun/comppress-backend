package org.comppress.comppressbackend.service.user;

import lombok.extern.slf4j.Slf4j;
import org.comppress.comppressbackend.dto.UserDto;
import org.comppress.comppressbackend.entity.Role;
import org.comppress.comppressbackend.entity.User;
import org.comppress.comppressbackend.exception.InvalidRequestException;
import org.comppress.comppressbackend.exception.ResourceNotFoundException;
import org.comppress.comppressbackend.mapper.MapstructMapper;
import org.comppress.comppressbackend.repository.RoleRepository;
import org.comppress.comppressbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MapstructMapper mapstructMapper;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, MapstructMapper mapstructMapper, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.mapstructMapper = mapstructMapper;
        this.roleRepository = roleRepository;
    }

    @Override
    public Long create(UserDto userDto) {
        if(userDto.getEmail()==null){
            log.error("Invalid User create Request, missing email on the create");
            throw new InvalidRequestException("Cannot create user, missing mandatory field");
        }
        if(userRepository.existsByEmail(userDto.getEmail())){
            log.error("Email already exist");
            throw new InvalidRequestException("Email already exist");
        }
        User user = mapstructMapper.userDtoToUser(userDto);
        Optional<Role> found = roleRepository.findByName(userDto.getRoleDto().getName());
        if(found.isPresent()){
            User saved = userRepository.save(user);
            log.info("Created User with id {}", saved.getId());
            saved.setRole(found.get());
            userRepository.save(saved);
            return saved.getId();
        }
        log.error("Invalid Role name provided {}", userDto);
        throw new InvalidRequestException("Invalid Role " + userDto.getRoleDto().getName() + " can not create User");
    }

    @Override
    public Optional<UserDto> findById(Long id) {
        Optional<User> found = userRepository.findById(id);
        if(found.isPresent()){
            log.info("Found User with id {}", id);
            return Optional.of(mapstructMapper.userToUserDto(found.get()));
        }
        log.error("No User found with id {}", id);
        throw new ResourceNotFoundException("User with id " + id + " not found");
    }

    @Override
    public Optional<UserDto> findByEmail(String email) {
        Optional<User> found = userRepository.findByEmail(email);
        if(found.isPresent()){
            log.info("Found User with email {}", email);
            return Optional.of(mapstructMapper.userToUserDto(found.get()));
        }
        log.error("No User found with email {}", email);
        return Optional.empty();
    }

    @Override
    public Optional<UserDto> findByUsername(String username) {
        Optional<User> found = userRepository.findByUsername(username);
        if(found.isPresent()){
            log.info("Found User with username {}", username);
            return Optional.of(mapstructMapper.userToUserDto(found.get()));
        }
        log.error("No User found with username {}", username);
        return Optional.empty();
    }

    @Override
    public List<UserDto> findAll() {
        List<User> userList = userRepository.findAll();
        log.info("Found user list of size {}", userList.size());
        return mapstructMapper.userListToUserListDto(userList);
    }
}
