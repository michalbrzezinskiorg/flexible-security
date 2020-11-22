package org.michalbrzezinski.securitate.feature.security;

import lombok.Builder;
import lombok.Value;
import org.michalbrzezinski.securitate.domain.security.PermissionDO;
import org.michalbrzezinski.securitate.domain.security.UserDO;

import java.time.ZonedDateTime;

@Value
public class AddPermissionEvent {
    ZonedDateTime created;
    PermissionDO payload;

    @Builder
    public AddPermissionEvent(ZonedDateTime created, PermissionDO payload) {
        this.created = created;
        this.payload = payload;
    }
}
