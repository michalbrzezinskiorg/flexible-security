package org.michalbrzezinski.securitate.domain.security.events;

import lombok.Builder;
import lombok.Value;
import org.michalbrzezinski.securitate.domain.security.objects.PermissionDO;

import java.time.ZonedDateTime;

@Value
@Builder
public class PermissionCreatedSystemEvent {
    PermissionDO payload;
    ZonedDateTime created;
}
