package org.michalbrzezinski.securitate.database.articles;

import org.michalbrzezinski.securitate.feature.articles.objects.Article;
import org.michalbrzezinski.securitate.feature.articles.objects.User;
import org.springframework.stereotype.Service;

@Service
class CustomArticleMapper {

    ArticleEntity map(Article article) {
        return ArticleEntity.builder()
                .author(map(article.getAuthor()))
                .body(article.getBody())
                .subject(article.getSubject())
                .url(article.getUrl())
                .build();
    }

    Article map(ArticleEntity article) {
        return Article.builder()
                .author(map(article.getAuthor()))
                .body(article.getBody())
                .subject(article.getSubject())
                .url(article.getUrl())
                .build();
    }

    AuthorEntity map(User author) {
        return AuthorEntity.builder()
                .id(author.getId())
                .name(author.getName())
                .surname(author.getSurname())
                .build();
    }

    User map(AuthorEntity author) {
        return User.builder()
                .id(author.getId())
                .name(author.getName())
                .surname(author.getSurname())
                .build();
    }
}
