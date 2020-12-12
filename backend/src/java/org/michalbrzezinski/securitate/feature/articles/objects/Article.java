package org.michalbrzezinski.securitate.feature.articles.objects;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Article {
    String url;
    User author;
    String subject;
    String body;
}
