package org.comppress.comppressbackend.service.role;

import lombok.extern.slf4j.Slf4j;
import org.comppress.comppressbackend.dto.RoleDto;
import org.comppress.comppressbackend.entity.Role;
import org.comppress.comppressbackend.exception.ResourceNotFoundException;
import org.comppress.comppressbackend.mapper.MapstructMapper;
import org.comppress.comppressbackend.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final MapstructMapper mapstructMapper;

    public RoleServiceImpl(RoleRepository roleRepository, MapstructMapper mapstructMapper) {
        this.roleRepository = roleRepository;
        this.mapstructMapper = mapstructMapper;
    }

    @Override
    public Long create(RoleDto roleDto) {
        Role role = mapstructMapper.roleDtoToRole(roleDto);
        Role saved = roleRepository.save(role);
        log.info("Created role with id {}", saved.getId());
        return saved.getId();
    }

    @Override
    public Optional<RoleDto> findById(Long id) {
        Optional<Role> found = roleRepository.findById(id);
        if(found.isPresent()){
            log.info("Found Role with id {}", id);
            return Optional.of(mapstructMapper.roleToRoleDto(found.get()));
        }
        log.error("No Role found with id {}", id);
        throw new ResourceNotFoundException("Role with id " + id + " not found");
    }

    @Override
    public Optional<RoleDto> findByName(String name) {
        Optional<Role> found = roleRepository.findByName(name);
        if(found.isPresent()){
            log.info("Found Role with name {}", name);
            return Optional.of(mapstructMapper.roleToRoleDto(found.get()));
        }
        log.error("No Role found with name {}", name);
        return Optional.empty();
    }

    @Override
    public List<RoleDto> findAll() {
        List<Role> roleList = roleRepository.findAll();
        log.info("Found role list of size {}", roleList.size());
        return mapstructMapper.roleListToRoleDtoList(roleList);
    }
}
