package org.michalbrzezinski.securitate.config.security.events;

import lombok.Builder;
import lombok.Value;
import org.michalbrzezinski.securitate.domain.security.RoleDO;

import java.time.ZonedDateTime;

@Value
public class AddRoleEvent {
    ZonedDateTime created;
    RoleDO payload;

    @Builder
    public AddRoleEvent(ZonedDateTime created, RoleDO payload) {
        this.created = created;
        this.payload = payload;
    }
}
