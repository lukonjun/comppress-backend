package org.comppress.comppressbackend.service;

import org.comppress.comppressbackend.dto.ArticleDto;
import org.comppress.comppressbackend.entity.Article;
import org.comppress.comppressbackend.entity.Source;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ArticleService {

    Long create(ArticleDto articleDto);
    Optional<ArticleDto> findById(Long id);
    List<ArticleDto> findAll(Map<String,String> queryParameter);

}
