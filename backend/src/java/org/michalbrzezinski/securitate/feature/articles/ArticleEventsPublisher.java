package org.michalbrzezinski.securitate.feature.articles;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.michalbrzezinski.securitate.feature.articles.events.CreateArticleEvent;
import org.michalbrzezinski.securitate.feature.articles.events.EditArticleEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class ArticleEventsPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

    public void publish(CreateArticleEvent e) {
        log.info(" ====> sending AddUserEvent event [{}]", e);
        executor.schedule(() -> applicationEventPublisher.publishEvent(e), 10, TimeUnit.SECONDS);
    }

    public void publish(EditArticleEvent e) {
        log.info(" ====> sending AddUserEvent event [{}]", e);
        executor.schedule(() -> applicationEventPublisher.publishEvent(e), 10, TimeUnit.SECONDS);
    }

}
