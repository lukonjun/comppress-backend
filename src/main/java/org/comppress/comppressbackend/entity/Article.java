package org.comppress.comppressbackend.entity;

import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
public class Article extends  AbstractEntity{

    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;
    private String content;
    @ManyToOne(cascade = CascadeType.ALL)
    private Source source;

}
