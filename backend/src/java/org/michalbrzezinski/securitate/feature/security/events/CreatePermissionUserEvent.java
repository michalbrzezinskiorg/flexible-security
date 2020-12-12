package org.michalbrzezinski.securitate.feature.security.events;

import lombok.Builder;
import lombok.Value;
import org.michalbrzezinski.securitate.feature.security.objects.Permission;

import java.time.ZonedDateTime;

@Value
public class CreatePermissionUserEvent {
    ZonedDateTime created;
    Permission payload;

    @Builder
    public CreatePermissionUserEvent(ZonedDateTime created, Permission payload) {
        this.created = created;
        this.payload = payload;
    }
}
