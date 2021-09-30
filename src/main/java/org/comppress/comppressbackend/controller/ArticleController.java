package org.comppress.comppressbackend.controller;

import lombok.extern.slf4j.Slf4j;
import org.comppress.comppressbackend.dto.ApiResponse;
import org.comppress.comppressbackend.dto.ArticleDto;
import org.comppress.comppressbackend.dto.UserDto;
import org.comppress.comppressbackend.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/article")
@Slf4j
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public ApiResponse<Long> create(@RequestBody ArticleDto articleDto){
        try{
            Long id = articleService.create(articleDto);
            return new ApiResponse<>(id,"Success",  200 );
        }catch (Exception e){
            log.error("Encountered an exception {} while creating a new Article",e.getMessage());
            return new ApiResponse<>(null,"Failed: " +  e.getMessage(), 501);
        }
    }

    @GetMapping("/{id}")
    public ApiResponse<ArticleDto> findById(@PathVariable Long id){
        try{
            Optional<ArticleDto> optionalRoleDto = articleService.findById(id);
            if(optionalRoleDto.isPresent()){
                return new ApiResponse<>(optionalRoleDto.get(),"Success",  200 );
            }else{
                return new ApiResponse<>(null,"User with" + id + " Not Found",  404 );
            }
        }catch (Exception e){
            log.error("Encountered an exception {} while finding a Article by id",e.getMessage());
            return new ApiResponse<>(null,"Failed: " +  e.getMessage(), 501);
        }
    }

    @GetMapping
    public ApiResponse<List<ArticleDto>> findAll(@RequestParam Map<String, String> queryParams){
        try{
            List<ArticleDto> articleDtoList = articleService.findAll(queryParams);
            return new ApiResponse<>(articleDtoList,"Success",  200);
        }catch (Exception e){
            log.error("Encountered an exception {} while fetching Articles",e.getMessage());
            return new ApiResponse<>(null,"Failed: " +  e.getMessage(), 501);
        }
    }

}
