package org.comppress.comppressbackend.service.user;

import org.comppress.comppressbackend.dto.RoleDto;
import org.comppress.comppressbackend.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Long create(UserDto userDto);
    Optional<UserDto> findById(Long id);
    Optional<UserDto> findByEmail(String email);
    Optional<UserDto> findByUsername(String username);
    List<UserDto> findAll();

}
