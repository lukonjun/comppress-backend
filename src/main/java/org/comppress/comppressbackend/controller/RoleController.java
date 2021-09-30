package org.comppress.comppressbackend.controller;

import lombok.extern.slf4j.Slf4j;
import org.comppress.comppressbackend.dto.ApiResponse;
import org.comppress.comppressbackend.dto.RoleDto;
import org.comppress.comppressbackend.service.role.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/role")
@Slf4j
public class RoleController {

    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ApiResponse<Long> create(@RequestBody RoleDto roleDto){
        try{
            Long id = roleService.create(roleDto);
            return new ApiResponse<>(id,"Success",  200 );
        }catch (Exception e){
            log.error("Encountered an exception {} while creating a new role",e.getMessage());
            return new ApiResponse<>(null,"Failed: " +  e.getMessage(), 501);
        }
    }

    @GetMapping("/{id}")
    public ApiResponse<RoleDto> findById(@PathVariable Long id){
        try{
            Optional<RoleDto> optionalRoleDto = roleService.findById(id);
            if(optionalRoleDto.isPresent()){
                return new ApiResponse<>(optionalRoleDto.get(),"Success",  200 );
            }else{
                return new ApiResponse<>(null,"Role with" + id + " Not Found",  404 );
            }
        }catch (Exception e){
            log.error("Encountered an exception {} while finding a role by id",e.getMessage());
            return new ApiResponse<>(null,"Failed: " +  e.getMessage(), 501);
        }
    }

    @GetMapping("/filter")
    public ApiResponse<RoleDto> findByName(@RequestParam String roleName){
        try{
            Optional<RoleDto> optionalRoleDto = roleService.findByName(roleName);
            if(optionalRoleDto.isPresent()){
                return new ApiResponse<>(optionalRoleDto.get(),"Success",  200 );
            }else{
                return new ApiResponse<>(null,"Role with" + roleName + " Not Found",  404 );
            }
        }catch (Exception e){
            log.error("Encountered an exception {} while finding a role by name",e.getMessage());
            return new ApiResponse<>(null,"Failed: " +  e.getMessage(), 501);
        }
    }

    @GetMapping
    public ApiResponse<List<RoleDto>> findAll(){
        try{
            List<RoleDto> roleDtoList = roleService.findAll();
            return new ApiResponse<>(roleDtoList,"Success",  200);
        }catch (Exception e){
            log.error("Encountered an exception {} while fetching all roles",e.getMessage());
            return new ApiResponse<>(null,"Failed: " +  e.getMessage(), 501);
        }
    }

}
