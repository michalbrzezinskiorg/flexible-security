package org.michalbrzezinski.securitate.domain.security.events;

import lombok.Builder;
import lombok.Value;
import org.michalbrzezinski.securitate.domain.security.objects.PermissionDO;

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
