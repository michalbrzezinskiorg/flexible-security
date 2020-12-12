package org.michalbrzezinski.securitate.feature.security.events;

import lombok.Builder;
import lombok.Value;
import org.michalbrzezinski.securitate.feature.security.objects.Role;

import java.time.ZonedDateTime;

@Value
@Builder
public class EditRoleAddControllerUserEvent {
    Role payload;
    ZonedDateTime created;
}
