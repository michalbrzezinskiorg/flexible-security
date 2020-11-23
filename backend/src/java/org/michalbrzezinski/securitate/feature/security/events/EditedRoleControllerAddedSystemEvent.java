package org.michalbrzezinski.securitate.feature.security.events;

import lombok.Builder;
import lombok.Value;
import org.michalbrzezinski.securitate.feature.security.objects.RoleDO;

import java.time.ZonedDateTime;

@Value
@Builder
public class EditedRoleControllerAddedSystemEvent {
    RoleDO payload;
    ZonedDateTime created;
}
