package org.comppress.comppressbackend.jsonmodel;

import lombok.Data;

import java.util.ArrayList;

@Data
public class NewsJsonModel {

    private String status;
    private int totalResults;
    private ArrayList<ArticleJsonModel> articles;

}
