package org.comppress.comppressbackend.client;

import com.google.gson.Gson;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.comppress.comppressbackend.dto.ArticleDto;
import org.comppress.comppressbackend.entity.Article;
import org.comppress.comppressbackend.jsonmodel.NewsJsonModel;
import org.comppress.comppressbackend.mapper.MapstructMapper;
import org.comppress.comppressbackend.repository.ArticleRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

@Component
@Slf4j
@EnableScheduling
public class NewsClient {

    private final String apiToken;

    private final MapstructMapper mapstructMapper;

    private final ArticleRepository articleRepository;

    @Autowired
    public NewsClient(@Value("${news-api.token:aDefaultUrl}") String apiToken, MapstructMapper mapstructMapper, ArticleRepository articleRepository) {
        this.apiToken = apiToken;
        this.mapstructMapper = mapstructMapper;
        this.articleRepository = articleRepository;
    }

    @Scheduled(fixedRate = 10 * 60 * 1000) // Every 10 minutes
    @Test
    public void fetchNews() throws URISyntaxException, IOException, InterruptedException {

        // Keyword or phrase. Eg: find all articles containing the word 'Microsoft'.
        // Date published. Eg: find all articles published yesterday.
        // Source domain name. Eg: find all articles published on thenextweb.com.
        // Language. Eg: find all articles written in English.
        // Find all News last 24h, from spiegel in german
        // Api Documentation https://newsapi.org/docs/endpoints/everything
        // https://newsapi.org/v2/everything?from=2021-09-26&domains=spiegel.de

        String queryParameter = "from=2021-09-26&domains=spiegel.de&apiKey=" + apiToken;
        String urlString = "https://newsapi.org/v2/everything" + "?" + queryParameter;

        log.info("Build HTTP request");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(urlString))
                .GET()
                .build();

        log.info("Send GET request to " + urlString);
        var response = HttpClient.newHttpClient().send(request,
                HttpResponse.BodyHandlers.ofString());

        log.info("Status code: " + response.statusCode());
        log.info("Headers: " + response.headers());

        // Code Example for Gson from here https://www.techiediaries.com/java/java-11-httpclient-gson-send-http-get-parse-json-example/
        NewsJsonModel news = new Gson().fromJson(response.body(), NewsJsonModel.class);
        ArrayList<ArticleDto> articles = new ArrayList<>();

        news.getArticles().forEach(articleJsonModel -> {
            articles.add(mapstructMapper.articleJsonModelToArticleDto(articleJsonModel));
        });

        ArrayList<Article> testEntityList = new ArrayList<>();
        articles.forEach(articleDto -> {
            testEntityList.add(mapstructMapper.articleDtoToArticle(articleDto));
        });

        log.info("Size of Entity List: " + testEntityList.size());

        // Duplicate Entry check needs to be done
        testEntityList.forEach(article -> {
            try {
                articleRepository.save(article);
            }catch (Exception e){
                log.error("Exception occurs during save of article " + article.getUrl());
                e.printStackTrace();
            }
        });

    }

}
