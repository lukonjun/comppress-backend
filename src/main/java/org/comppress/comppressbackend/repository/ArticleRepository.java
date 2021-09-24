package org.comppress.comppressbackend.repository;

import org.comppress.comppressbackend.entity.Article;
import org.comppress.comppressbackend.entity.Source;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article,Long> {

    List<Article> findByTitle(String Title);
    List<Article> findByAuthor(String Author);
    List<Article> findBySource(Source source);

}
