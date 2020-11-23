package org.michalbrzezinski.securitate.feature.security.events;

import lombok.Builder;
import lombok.Value;
import org.michalbrzezinski.securitate.feature.security.objects.ControllerDO;

import java.time.ZonedDateTime;

@Value
public class CreateControllerSystemEvent {
    ZonedDateTime created;
    ControllerDO payload;

    @Builder
    public CreateControllerSystemEvent(ZonedDateTime created, ControllerDO payload) {
        this.created = created;
        this.payload = payload;
    }
}
