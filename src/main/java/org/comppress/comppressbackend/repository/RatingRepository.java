package org.comppress.comppressbackend.repository;

import org.comppress.comppressbackend.entity.Article;
import org.comppress.comppressbackend.entity.Rating;
import org.comppress.comppressbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating,Long> {

    List<Rating> findByUser(User user);
    List<Article> findByArticle(Article article);

}
