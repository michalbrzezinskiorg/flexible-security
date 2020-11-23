package org.michalbrzezinski.securitate.feature.security.events;

import lombok.Builder;
import lombok.Value;
import org.michalbrzezinski.securitate.feature.security.objects.PermissionDO;

import java.time.ZonedDateTime;

@Value
public class CreatePermissionUserEvent {
    ZonedDateTime created;
    PermissionDO payload;

    @Builder
    public CreatePermissionUserEvent(ZonedDateTime created, PermissionDO payload) {
        this.created = created;
        this.payload = payload;
    }
}
