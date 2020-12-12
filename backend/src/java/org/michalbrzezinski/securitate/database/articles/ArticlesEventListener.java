package org.michalbrzezinski.securitate.database.articles;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.michalbrzezinski.securitate.feature.articles.events.CreateArticleEvent;
import org.michalbrzezinski.securitate.feature.articles.events.EditArticleEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class ArticlesEventListener {

    private final ArticleCommandService articleCommandService;

    @Async
    @EventListener
    public void addAddControllerEvent(CreateArticleEvent event) {
        log.info("#===>  received event [{}]", event);
        articleCommandService.save(event.getPayload());
    }

    @Async
    @EventListener
    public void addAddControllerEvent(EditArticleEvent event) {
        log.info("#===>  received event [{}]", event);
        articleCommandService.save(event.getPayload());
    }
}
