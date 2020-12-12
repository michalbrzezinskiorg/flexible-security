package org.michalbrzezinski.securitate.feature.security.events;

import lombok.Builder;
import lombok.Value;
import org.michalbrzezinski.securitate.feature.security.objects.Role;

import java.time.ZonedDateTime;

@Value
public class CreateRoleSystemEvent {
    ZonedDateTime created;
    Role payload;

    @Builder
    public CreateRoleSystemEvent(ZonedDateTime created, Role payload) {
        this.created = created;
        this.payload = payload;
    }
}
