package org.michalbrzezinski.securitate.feature.security.events;

import lombok.Builder;
import lombok.Value;
import org.michalbrzezinski.securitate.feature.security.objects.User;

import java.time.ZonedDateTime;

@Value
public class CreateUserSystemEvent {
    ZonedDateTime created;
    User payload;

    @Builder
    public CreateUserSystemEvent(ZonedDateTime created, User payload) {
        this.created = created;
        this.payload = payload;
    }
}
