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
public class PermissionDO {
    Long id;
    ZonedDateTime fromDate;
    ZonedDateTime toDate;
    boolean active;
    UserDO createdBy;
    UserDO permissionFor;
    @Singular
    Set<ControllerDO> controllers;
}
