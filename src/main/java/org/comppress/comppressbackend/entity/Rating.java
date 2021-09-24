package org.comppress.comppressbackend.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
public class Rating extends AbstractEntity {

    private Integer credibility;
    private Integer factuality;
    private Integer neutrality;
    @ManyToOne
    private Article article;
    @ManyToOne
    private User user;

}
