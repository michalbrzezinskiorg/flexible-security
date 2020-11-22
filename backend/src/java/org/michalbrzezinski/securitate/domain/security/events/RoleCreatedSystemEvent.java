package org.michalbrzezinski.securitate.domain.security.events;

import lombok.Builder;
import lombok.Value;
import org.michalbrzezinski.securitate.domain.security.objects.RoleDO;

import java.time.ZonedDateTime;

@Value
@Builder
public class RoleCreatedSystemEvent {
    RoleDO payload;
    ZonedDateTime created;
}
