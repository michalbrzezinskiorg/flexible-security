package org.michalbrzezinski.securitate.feature.security;

import lombok.Builder;
import lombok.Value;
import org.michalbrzezinski.securitate.domain.security.RoleDO;

import java.time.ZonedDateTime;

@Value
@Builder
public class AddControllerToRole {
    RoleDO payload;
    ZonedDateTime created;
}
