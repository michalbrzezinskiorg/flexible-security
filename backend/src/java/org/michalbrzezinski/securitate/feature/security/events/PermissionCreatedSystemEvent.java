package org.michalbrzezinski.securitate.feature.security.events;

import lombok.Builder;
import lombok.Value;
import org.michalbrzezinski.securitate.feature.security.objects.PermissionDO;

import java.time.ZonedDateTime;

@Value
@Builder
public class PermissionCreatedSystemEvent {
    PermissionDO payload;
    ZonedDateTime created;
}
