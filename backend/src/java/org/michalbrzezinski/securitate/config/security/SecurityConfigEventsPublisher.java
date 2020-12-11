package org.michalbrzezinski.securitate.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.michalbrzezinski.securitate.feature.security.events.CreateControllerSystemEvent;
import org.michalbrzezinski.securitate.feature.security.events.CreateRoleSystemEvent;
import org.michalbrzezinski.securitate.feature.security.events.CreateUserSystemEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
@Slf4j
class SecurityConfigEventsPublisher {

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
}
