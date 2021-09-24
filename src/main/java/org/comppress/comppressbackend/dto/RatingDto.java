package org.comppress.comppressbackend.dto;

import lombok.Data;
import org.comppress.comppressbackend.entity.Article;
import org.comppress.comppressbackend.entity.User;

@Data
public class RatingDto {

    private Integer credibility;
    private Integer factuality;
    private Integer neutrality;
    private Article article;
    private User user;

}
