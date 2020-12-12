package org.michalbrzezinski.securitate.feature.articles;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.michalbrzezinski.securitate.config.security.LoggedUser;
import org.michalbrzezinski.securitate.config.security.objects.Actor;
import org.michalbrzezinski.securitate.feature.articles.events.CreateArticleEvent;
import org.michalbrzezinski.securitate.feature.articles.events.EditArticleEvent;
import org.michalbrzezinski.securitate.feature.articles.objects.Article;
import org.michalbrzezinski.securitate.feature.articles.objects.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

import static org.michalbrzezinski.securitate.feature.articles.Slugger.sluggify;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleManagementService {

    private final ArticleDatabase articleDatabase;
    private final ArticleEventsPublisher articleEventsPublisher;
    private final LoggedUser loggedUser;

    public void createArticle(Article article) {
        Actor actor = loggedUser.getLoggedUser().orElseThrow(IllegalAccessError::new);
        Article compiled = Article.builder()
                .author(User.builder()
                        .name(actor.getName())
                        .surname(actor.getSurname())
                        .build())
                .subject(article.getSubject())
                .body(article.getBody())
                .url(sluggify(article.getSubject()))
                .build();
        articleEventsPublisher.publish(CreateArticleEvent.builder().created(ZonedDateTime.now()).payload(compiled).build());
    }

    public void editArticle(String url, Article article) {
        Actor actor = loggedUser.getLoggedUser()
                .orElseThrow(IllegalAccessError::new);
        Article edited = Article.builder()
                .author(User.builder()
                        .id(actor.getId())
                        .name(actor.getName())
                        .surname(actor.getSurname())
                        .build())
                .subject(article.getSubject())
                .body(article.getBody())
                .url(url)
                .build();
        articleEventsPublisher.publish(EditArticleEvent.builder()
                .payload(edited).created(ZonedDateTime.now()).build());
    }

    public Article findByUrl(String url) {
        return articleDatabase.findByUrl(url).orElseThrow(IllegalArgumentException::new);
    }

    public Page<Article> findAll(Pageable pageable) {
        return articleDatabase.findAll(pageable);
    }
}
