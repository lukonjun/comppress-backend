package org.comppress.comppressbackend.controller;

import lombok.extern.slf4j.Slf4j;
import org.comppress.comppressbackend.dto.ApiResponse;
import org.comppress.comppressbackend.dto.RoleDto;
import org.comppress.comppressbackend.dto.UserDto;
import org.comppress.comppressbackend.service.user.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ApiResponse<Long> create(@RequestBody UserDto userDto){
        try{
            Long id = userService.create(userDto);
            return new ApiResponse<>(id,"Success",  200 );
        }catch (Exception e){
            log.error("Encountered an exception {} while creating a new user",e.getMessage());
            return new ApiResponse<>(null,"Failed: " +  e.getMessage(), 501);
        }
    }

    @GetMapping("/{id}")
    public ApiResponse<UserDto> findById(@PathVariable Long id){
        try{
            Optional<UserDto> optionalRoleDto = userService.findById(id);
            if(optionalRoleDto.isPresent()){
                return new ApiResponse<>(optionalRoleDto.get(),"Success",  200 );
            }else{
                return new ApiResponse<>(null,"User with" + id + " Not Found",  404 );
            }
        }catch (Exception e){
            log.error("Encountered an exception {} while finding a User by id",e.getMessage());
            return new ApiResponse<>(null,"Failed: " +  e.getMessage(), 501);
        }
    }

    @GetMapping("/filter")
    public ApiResponse<UserDto> findByEmail(@RequestParam String email){
        try{
            Optional<UserDto> optionalRoleDto = userService.findByEmail(email);
            if(optionalRoleDto.isPresent()){
                return new ApiResponse<>(optionalRoleDto.get(),"Success",  200 );
            }else{
                return new ApiResponse<>(null,"Role with email" + email + " Not Found",  404 );
            }
        }catch (Exception e){
            log.error("Encountered an exception {} while finding a role by email",e.getMessage());
            return new ApiResponse<>(null,"Failed: " +  e.getMessage(), 501);
        }
    }

    @GetMapping("/username/{username}")
    public ApiResponse<UserDto> findByUsername(@PathVariable String username){
        try{
            Optional<UserDto> optionalRoleDto = userService.findByUsername(username);
            if(optionalRoleDto.isPresent()){
                return new ApiResponse<>(optionalRoleDto.get(),"Success",  200 );
            }else{
                return new ApiResponse<>(null,"Role with username" + username + " Not Found",  404 );
            }
        }catch (Exception e){
            log.error("Encountered an exception {} while finding a role by username",e.getMessage());
            return new ApiResponse<>(null,"Failed: " +  e.getMessage(), 501);
        }
    }

    @GetMapping
    public ApiResponse<List<UserDto>> findAll(){
        try{
            List<UserDto> userDtoList = userService.findAll();
            return new ApiResponse<>(userDtoList,"Success",  200);
        }catch (Exception e){
            log.error("Encountered an exception {} while fetching all Users",e.getMessage());
            return new ApiResponse<>(null,"Failed: " +  e.getMessage(), 501);
        }
    }

}
