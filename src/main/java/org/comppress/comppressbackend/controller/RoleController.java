package org.comppress.comppressbackend.controller;

import lombok.extern.slf4j.Slf4j;
import org.comppress.comppressbackend.dto.ApiResponse;
import org.comppress.comppressbackend.dto.PreferenceDto;
import org.comppress.comppressbackend.dto.RoleDto;
import org.comppress.comppressbackend.service.role.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/roles")
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
        return new ApiResponse<>(roleService.findById(id).get(),"Success",  200 );
    }

    @GetMapping
    public ApiResponse<List<RoleDto>> find(@RequestParam Map<String, String> requestParameter){
        try{
            if(requestParameter == null || requestParameter.size() == 0){
                List<RoleDto> roleDtoList = roleService.findAll();
                return new ApiResponse<>(roleDtoList,"Success",  200);
            }else if(requestParameter.containsKey("name")){
                Optional<RoleDto> optionalRoleDto = roleService.findByName(requestParameter.get("name"));
                if(optionalRoleDto.isPresent()){
                    return new ApiResponse<>(Collections.singletonList(optionalRoleDto.get()),"Success",  200 );
                }else{
                    return new ApiResponse<>(null,"Role with" + requestParameter.get("name") + " Not Found",  404 );
                }
            }
        }catch (Exception e){
            log.error("Encountered an exception {} while finding a role",e.getMessage());
            return new ApiResponse<>(null,"Failed: " +  e.getMessage(), 501);
        }
        log.error("Query Parameters Invalid");
        return new ApiResponse<>(null,"Query Parameter Invalid", 501);
    }

}
