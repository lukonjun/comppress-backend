package org.comppress.comppressbackend.controller;

import lombok.extern.slf4j.Slf4j;
import org.comppress.comppressbackend.dto.ApiResponse;
import org.comppress.comppressbackend.dto.PreferenceDto;
import org.comppress.comppressbackend.service.preference.PreferenceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/preference")
@Slf4j
public class PreferenceController {

    private final PreferenceService preferenceService;

    public PreferenceController(PreferenceService preferenceService) {
        this.preferenceService = preferenceService;
    }

    @PostMapping
    public ApiResponse<Long> create(@RequestBody PreferenceDto preferenceDto){
        try{
            Long id = preferenceService.create(preferenceDto);
            return new ApiResponse<>(id,"Success",  200 );
        }catch (Exception e){
            log.error("Encountered an exception {} while creating a new preference",e.getMessage());
            return new ApiResponse<>(null,"Failed: " +  e.getMessage(), 501);
        }
    }

    @GetMapping("/{id}")
    public ApiResponse<PreferenceDto> findById(@PathVariable Long id){
        try{
            Optional<PreferenceDto> optionalRoleDto = preferenceService.findById(id);
            if(optionalRoleDto.isPresent()){
                return new ApiResponse<>(optionalRoleDto.get(),"Success",  200 );
            }else{
                return new ApiResponse<>(null,"Preference with" + id + " Not Found",  404 );
            }
        }catch (Exception e){
            log.error("Encountered an exception {} while finding a Preference by id",e.getMessage());
            return new ApiResponse<>(null,"Failed: " +  e.getMessage(), 501);
        }
    }

    @GetMapping("/filter")
    public ApiResponse<PreferenceDto> findByNameAndType(@RequestParam String name, @RequestParam String type){
        try{
            Optional<PreferenceDto> optionalRoleDto = preferenceService.findByNameAndType(name,type);
            if(optionalRoleDto.isPresent()){
                return new ApiResponse<>(optionalRoleDto.get(),"Success",  200 );
            }else{
                return new ApiResponse<>(null,"Preference with name" + name + " and type " + type + " Not Found",  404 );
            }
        }catch (Exception e){
            log.error("Encountered an exception {} while finding a Preference by name and type",e.getMessage());
            return new ApiResponse<>(null,"Failed: " +  e.getMessage(), 501);
        }
    }

    @GetMapping("/type/{type}")
    public ApiResponse<List<PreferenceDto>> findByType(@PathVariable String type){
        try{
            List<PreferenceDto> preferenceDtoList = preferenceService.findByType(type);
            return new ApiResponse<>(preferenceDtoList,"Success",  200);
        }catch (Exception e){
            log.error("Encountered an exception {} while finding a Preference by type",e.getMessage());
            return new ApiResponse<>(null,"Failed: " +  e.getMessage(), 501);
        }
    }

    @GetMapping
    public ApiResponse<List<PreferenceDto>> findAll(){
        try{
            List<PreferenceDto> roleDtoList = preferenceService.findAll();
            return new ApiResponse<>(roleDtoList,"Success",  200);
        }catch (Exception e){
            log.error("Encountered an exception {} while fetching all Preferences",e.getMessage());
            return new ApiResponse<>(null,"Failed: " +  e.getMessage(), 501);
        }
    }

}
