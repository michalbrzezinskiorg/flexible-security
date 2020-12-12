package org.michalbrzezinski.securitate.feature.security.objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Set;

@Value
@Builder
@AllArgsConstructor
public class Role {
    int id;
    String name;
    @Singular
    Set<Controller> controllers;
    @Singular
    Set<User> users;
    Boolean active;
    LocalDateTime created;
    LocalDateTime edited;
    String createdBy;
    String editedBy;
}
