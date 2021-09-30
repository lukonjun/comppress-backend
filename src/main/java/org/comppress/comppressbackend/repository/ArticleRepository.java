package org.comppress.comppressbackend.repository;

import org.comppress.comppressbackend.entity.Article;
import org.comppress.comppressbackend.entity.Source;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article,Long> {

    List<Article> findByTitle(String title);
    List<Article> findByAuthor(String author);
    List<Article> findByAuthorAndTitle(String author, String title);
    List<Article> findBySource(Source source);

}
