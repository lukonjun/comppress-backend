package org.comppress.comppressbackend.service;

import lombok.extern.slf4j.Slf4j;
import org.comppress.comppressbackend.dto.ArticleDto;
import org.comppress.comppressbackend.entity.Article;
import org.comppress.comppressbackend.entity.Source;
import org.comppress.comppressbackend.mapper.MapstructMapper;
import org.comppress.comppressbackend.repository.ArticleRepository;
import org.comppress.comppressbackend.repository.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final MapstructMapper mapstructMapper;
    private final SourceRepository sourceRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository, MapstructMapper mapstructMapper, SourceRepository sourceRepository) {
        this.articleRepository = articleRepository;
        this.mapstructMapper = mapstructMapper;
        this.sourceRepository = sourceRepository;
    }

    @Override
    public Long create(ArticleDto articleDto) {
        Article article = mapstructMapper.articleDtoToArticle(articleDto);
        Optional<Source> found = sourceRepository.findByName(articleDto.getSource().getName());
        if(found.isPresent()){
            Article saved = articleRepository.save(article);
            log.info("Created Article with id {}", saved.getId());
            // Does this prduce a different outcome?
            saved.setSource(found.get());
            articleRepository.save(saved);
            return saved.getId();
        }
        log.error("Invalid Source name provided {}", articleDto);
        throw new RuntimeException("Invalid Source " + articleDto.getSource().getName() + " can not create Article");
    }

    @Override
    public Optional<ArticleDto> findById(Long id) {
        Optional<Article> found = articleRepository.findById(id);
        if(found.isPresent()){
            log.info("Found Article with id {}", id);
            return Optional.of(mapstructMapper.articleToArticleDto(found.get()));
        }
        log.error("No User found with id {}", id);
        return Optional.empty();
    }

    @Override
    public List<ArticleDto> findAll(Map<String,String> queryParameter) {
        List<Article> articleList = new ArrayList<>();
        if(queryParameter.size() == 0){
            articleList.addAll(articleRepository.findAll());
        } else if(queryParameter.containsKey("author") && queryParameter.containsKey("title")){
            articleList.addAll(articleRepository.findByAuthorAndTitle(queryParameter.get("author"),queryParameter.get("title")));
        } else if(queryParameter.containsKey("author")){
            articleList.addAll(articleRepository.findByAuthor(queryParameter.get("author")));
        } else if(queryParameter.containsKey("title")){
            articleList.addAll(articleRepository.findByTitle(queryParameter.get("title")));
        }
        log.error("Invalid Query Parameters passed");
        throw new RuntimeException("Invalid Query Parameters" + queryParameter + " passed");
    }
}
