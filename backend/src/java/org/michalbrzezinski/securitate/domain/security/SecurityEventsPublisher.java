package org.michalbrzezinski.securitate.domain.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.michalbrzezinski.securitate.domain.security.events.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
@Slf4j
public class SecurityEventsPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

    public void publish(CreateControllerSystemEvent e) {
        log.info(" ====> sending AddControllerEvent event [{}]", e);
        executor.schedule(() -> applicationEventPublisher.publishEvent(e), 10, TimeUnit.SECONDS);
    }

    public void publish(CreateRoleSystemEvent e) {
        log.info(" ====> sending AddRoleEvent event [{}]", e);
        executor.schedule(() -> applicationEventPublisher.publishEvent(e), 10, TimeUnit.SECONDS);
    }

    public void publish(CreateUserSystemEvent e) {
        log.info(" ====> sending AddUserEvent event [{}]", e);
        executor.schedule(() -> applicationEventPublisher.publishEvent(e), 10, TimeUnit.SECONDS);
    }

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
