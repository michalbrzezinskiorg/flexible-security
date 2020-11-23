package org.michalbrzezinski.securitate.feature.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.michalbrzezinski.securitate.feature.security.events.CreatePermissionUserEvent;
import org.michalbrzezinski.securitate.feature.security.events.CreateRoleUserEvent;
import org.michalbrzezinski.securitate.feature.security.events.EditRoleAddControllerUserEvent;
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

    public void publish(CreatePermissionUserEvent e) {
        log.info(" ====> sending AddUserEvent event [{}]", e);
        executor.schedule(() -> applicationEventPublisher.publishEvent(e), 10, TimeUnit.SECONDS);
    }

    public void publish(EditRoleAddControllerUserEvent e) {
        log.info(" ====> sending AddUserEvent event [{}]", e);
        executor.schedule(() -> applicationEventPublisher.publishEvent(e), 10, TimeUnit.SECONDS);
    }

    public void publish(CreateRoleUserEvent e) {
        log.info(" ====> sending AddUserEvent event [{}]", e);
        executor.schedule(() -> applicationEventPublisher.publishEvent(e), 10, TimeUnit.SECONDS);
    }
}
