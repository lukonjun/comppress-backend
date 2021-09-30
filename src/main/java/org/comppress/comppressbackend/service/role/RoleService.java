package org.comppress.comppressbackend.service.role;

import org.comppress.comppressbackend.dto.RoleDto;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    Long create(RoleDto roleDto);
    Optional<RoleDto> findById(Long id);
    Optional<RoleDto> findByName(String name);
    List<RoleDto> findAll();

}
