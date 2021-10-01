package org.comppress.comppressbackend.controller;

import lombok.extern.slf4j.Slf4j;
import org.comppress.comppressbackend.dto.ApiResponse;
import org.comppress.comppressbackend.dto.PreferenceDto;
import org.comppress.comppressbackend.service.preference.PreferenceService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/preferences")
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

    @GetMapping
    public ApiResponse<List<PreferenceDto>> find(@RequestParam Map<String, String> requestParameter){
        try{
            if(requestParameter == null || requestParameter.size() == 0){
                List<PreferenceDto> roleDtoList = preferenceService.findAll();
                return new ApiResponse<>(roleDtoList,"Success",  200);
            }else if(requestParameter.containsKey("type") && requestParameter.containsKey("name")){
                Optional<PreferenceDto> optionalPreferenceDto = preferenceService.findByNameAndType(requestParameter.get("name"),requestParameter.get("type"));
                if(optionalPreferenceDto.isPresent()){
                    return new ApiResponse<>(Collections.singletonList(optionalPreferenceDto.get()),"Success",  200 );
                }else{
                    return new ApiResponse<>(null,"Preference with name" + requestParameter.get("name") + " and type " + requestParameter.get("type") + " Not Found",  404 );
                }
            }else if(requestParameter.containsKey("type")){
                List<PreferenceDto> preferenceDtoList = preferenceService.findByType(requestParameter.get("type"));
                return new ApiResponse<>(preferenceDtoList,"Success",  200);
            }
        }catch (Exception e){
            log.error("Encountered an exception {} while fetching all Preferences",e.getMessage());
            return new ApiResponse<>(null,"Failed: " +  e.getMessage(), 501);
        }
        log.error("Query Parameters Invalid");
        return new ApiResponse<>(null,"Query Parameter Invalid", 501);
    }

}
