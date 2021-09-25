package org.comppress.comppressbackend.jsonmodel;

import lombok.Data;

@Data
public class ArticleJsonModel {

    private SourceJsonModel source;
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;
    private String content;

}
