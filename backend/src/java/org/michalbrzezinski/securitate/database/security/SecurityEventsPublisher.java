package org.michalbrzezinski.securitate.database.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.michalbrzezinski.securitate.feature.security.events.EditedRoleControllerAddedSystemEvent;
import org.michalbrzezinski.securitate.feature.security.events.PermissionCreatedSystemEvent;
import org.michalbrzezinski.securitate.feature.security.events.RoleCreatedSystemEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
@Slf4j
class SecurityEventsPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

    public void publish(PermissionCreatedSystemEvent e) {
        log.info(" ====> sending CreatedPermissionEvent event [{}]", e);
        executor.schedule(() -> applicationEventPublisher.publishEvent(e), 10, TimeUnit.SECONDS);
    }

    public void publish(EditedRoleControllerAddedSystemEvent e) {
        log.info(" ====> sending AddedControllerToRoleEvent event [{}]", e);
        executor.schedule(() -> applicationEventPublisher.publishEvent(e), 10, TimeUnit.SECONDS);
    }

    public void publish(RoleCreatedSystemEvent e) {
        log.info(" ====> sending NewRoleCreated event [{}]", e);
        executor.schedule(() -> applicationEventPublisher.publishEvent(e), 10, TimeUnit.SECONDS);
    }
}
