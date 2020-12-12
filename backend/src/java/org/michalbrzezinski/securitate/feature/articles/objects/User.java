package org.michalbrzezinski.securitate.feature.articles.objects;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class User {
    Integer id;
    String name;
    String surname;
}
