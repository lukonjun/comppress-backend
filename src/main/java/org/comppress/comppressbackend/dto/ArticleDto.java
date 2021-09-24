package org.comppress.comppressbackend.dto;

import lombok.Data;
import org.comppress.comppressbackend.entity.Source;

@Data
public class ArticleDto {

    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;
    private String content;
    private Source source;

}
