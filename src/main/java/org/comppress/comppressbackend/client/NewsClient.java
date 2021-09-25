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
@Data
@EnableScheduling
public class NewsClient {

    public static final String API_TOKEN = "f02dced1b8b64e6bb04e6443cf4d02e8";

    private final MapstructMapper mapstructMapper;

    private final ArticleRepository articleRepository;

    @Scheduled(fixedRate = 10 * 60 * 1000) // Every 10 minutes
    @Test
    public void fetchNews() throws URISyntaxException, IOException, InterruptedException {

        String queryParameter = "country=de&apiKey=" + API_TOKEN;
        String urlString = "https://newsapi.org/v2/top-headlines" + "?" + queryParameter;

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

        testEntityList.forEach(article -> {
            articleRepository.save(article);
        });

    }

}
