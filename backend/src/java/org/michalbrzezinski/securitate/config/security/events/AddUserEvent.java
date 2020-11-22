package org.michalbrzezinski.securitate.config.security.events;

import lombok.Builder;
import lombok.Value;
import org.michalbrzezinski.securitate.domain.security.UserDO;

import java.time.ZonedDateTime;

@Value
public class AddUserEvent {
    ZonedDateTime created;
    UserDO payload;

    @Builder
    public AddUserEvent(ZonedDateTime created, UserDO payload) {
        this.created = created;
        this.payload = payload;
    }
}
