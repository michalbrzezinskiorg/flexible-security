package org.michalbrzezinski.securitate.config.security.events;

import lombok.Builder;
import lombok.Value;
import org.michalbrzezinski.securitate.domain.security.ControllerDO;

import java.time.ZonedDateTime;

@Value
public class AddControllerEvent {
    ZonedDateTime created;
    ControllerDO payload;

    @Builder
    public AddControllerEvent(ZonedDateTime created, ControllerDO payload) {
        this.created = created;
        this.payload = payload;
    }
}
