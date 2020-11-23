package org.michalbrzezinski.securitate.feature.security.events;

import lombok.Builder;
import lombok.Value;
import org.michalbrzezinski.securitate.feature.security.objects.UserDO;

import java.time.ZonedDateTime;

@Value
public class CreateUserSystemEvent {
    ZonedDateTime created;
    UserDO payload;

    @Builder
    public CreateUserSystemEvent(ZonedDateTime created, UserDO payload) {
        this.created = created;
        this.payload = payload;
    }
}
