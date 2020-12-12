package org.michalbrzezinski.securitate.feature.security.events;

import lombok.Builder;
import lombok.Value;
import org.michalbrzezinski.securitate.feature.security.objects.Controller;

import java.time.ZonedDateTime;

@Value
public class CreateControllerSystemEvent {
    ZonedDateTime created;
    Controller payload;

    @Builder
    public CreateControllerSystemEvent(ZonedDateTime created, Controller payload) {
        this.created = created;
        this.payload = payload;
    }
}
