package org.michalbrzezinski.securitate.domain.security.events;

import lombok.Builder;
import lombok.Value;
import org.michalbrzezinski.securitate.domain.security.objects.RoleDO;

import java.time.ZonedDateTime;

@Value
public class CreateRoleSystemEvent {
    ZonedDateTime created;
    RoleDO payload;

    @Builder
    public CreateRoleSystemEvent(ZonedDateTime created, RoleDO payload) {
        this.created = created;
        this.payload = payload;
    }
}
