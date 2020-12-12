package org.michalbrzezinski.securitate.feature.articles.events;

import lombok.Builder;
import lombok.Value;
import org.michalbrzezinski.securitate.feature.articles.objects.Article;

import java.time.ZonedDateTime;

@Value
@Builder
public class EditArticleEvent {
    ZonedDateTime created;
    Article payload;
}
