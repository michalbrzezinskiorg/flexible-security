package org.michalbrzezinski.securitate.feature.security.events;

import lombok.Builder;
import lombok.Value;
import org.michalbrzezinski.securitate.feature.security.objects.Permission;

import java.time.ZonedDateTime;

@Value
@Builder
public class PermissionCreatedSystemEvent {
    Permission payload;
    ZonedDateTime created;
}
