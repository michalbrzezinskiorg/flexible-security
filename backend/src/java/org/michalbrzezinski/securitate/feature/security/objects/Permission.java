package org.michalbrzezinski.securitate.feature.security.objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.time.ZonedDateTime;
import java.util.Set;

@Value
@Builder
@AllArgsConstructor
public class Permission {
    Long id;
    ZonedDateTime fromDate;
    ZonedDateTime toDate;
    boolean active;
    String createdBy;
    String permissionFor;
    @Singular
    Set<Controller> controllers;
}
