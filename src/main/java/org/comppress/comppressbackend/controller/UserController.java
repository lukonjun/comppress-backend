package org.comppress.comppressbackend.controller;

import lombok.extern.slf4j.Slf4j;
import org.comppress.comppressbackend.dto.ApiResponse;
import org.comppress.comppressbackend.dto.PreferenceDto;
import org.comppress.comppressbackend.dto.RoleDto;
import org.comppress.comppressbackend.dto.UserDto;
import org.comppress.comppressbackend.service.user.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ApiResponse<Long> create(@RequestBody UserDto userDto){
        return new ApiResponse<>(userService.create(userDto),"Success",  200 );
    }

    @GetMapping("/{id}")
    public ApiResponse<UserDto> findById(@PathVariable Long id){
        return new ApiResponse<>(userService.findById(id).get(),"Success",  200 );
    }

    @GetMapping
    public ApiResponse<List<UserDto>> find(@RequestParam Map<String, String> requestParameter){
        try{
            if(requestParameter == null || requestParameter.size() == 0){
                List<UserDto> userDtoList = userService.findAll();
                return new ApiResponse<>(userDtoList,"Success",  200);
            }else if(requestParameter.containsKey("username")){
                Optional<UserDto> optionalUserDto = userService.findByUsername(requestParameter.get("username"));
                if(optionalUserDto.isPresent()){
                    return new ApiResponse<>(Collections.singletonList(optionalUserDto.get()),"Success",  200 );
                }else{
                    return new ApiResponse<>(null,"User with username" + requestParameter.get("username") + " Not Found",  404 );
                }
            }else if(requestParameter.containsKey("email")){
                Optional<UserDto> optionalUserDto = userService.findByEmail(requestParameter.get("email"));
                if(optionalUserDto.isPresent()){
                    return new ApiResponse<>(Collections.singletonList(optionalUserDto.get()),"Success",  200 );
                }else{
                    return new ApiResponse<>(null,"User with email" + requestParameter.get("email") + " Not Found",  404 );
                }
            }
        }catch (Exception e){
            log.error("Encountered an exception {} while fetching all Users",e.getMessage());
            return new ApiResponse<>(null,"Failed: " +  e.getMessage(), 501);
        }
        log.error("Query Parameters Invalid");
        return new ApiResponse<>(null,"Query Parameter Invalid", 501);
    }


}
