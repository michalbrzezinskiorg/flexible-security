package org.michalbrzezinski.securitate.feature.articles;

import org.michalbrzezinski.securitate.feature.articles.objects.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ArticleDatabase {
    Page<Article> findAll(Pageable pageable);

    Optional<Article> findByUrl(String url);
}
