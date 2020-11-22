package org.michalbrzezinski.securitate.domain.security.objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Set;

@Value
@Builder
@AllArgsConstructor
public class RoleDO {
    int id;
    String name;
    @Singular
    Set<ControllerDO> controllers;
    @Singular
    Set<UserDO> users;
    Boolean active;
    LocalDateTime created;
    LocalDateTime edited;
    String createdBy;
    String editedBy;
}
