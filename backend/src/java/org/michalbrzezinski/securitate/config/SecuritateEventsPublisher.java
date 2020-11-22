package org.michalbrzezinski.securitate.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.michalbrzezinski.securitate.config.security.events.AddControllerEvent;
import org.michalbrzezinski.securitate.config.security.events.AddRoleEvent;
import org.michalbrzezinski.securitate.config.security.events.AddUserEvent;
import org.michalbrzezinski.securitate.domain.security.PermissionDO;
import org.michalbrzezinski.securitate.feature.security.AddControllerToRole;
import org.michalbrzezinski.securitate.feature.security.AddPermissionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
@Slf4j
public class SecuritateEventsPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;
    ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

    public void publish(AddControllerEvent e) {
        log.info("sending AddControllerEvent event [{}]", e);
       executor.schedule(()-> applicationEventPublisher.publishEvent(e), 10, TimeUnit.SECONDS);
    }
    public void publish(AddRoleEvent e) {
        log.info("sending AddRoleEvent event [{}]", e);
        executor.schedule(()-> applicationEventPublisher.publishEvent(e), 10, TimeUnit.SECONDS);
    }
    public void publish(AddUserEvent e) {
        log.info("sending AddUserEvent event [{}]", e);
        executor.schedule(()-> applicationEventPublisher.publishEvent(e), 10, TimeUnit.SECONDS);
    }

    public void publish(AddPermissionEvent e) {
        log.info("sending AddUserEvent event [{}]", e);
        executor.schedule(()-> applicationEventPublisher.publishEvent(e), 10, TimeUnit.SECONDS);
    }

    public void publish(AddControllerToRole e) {
        log.info("sending AddUserEvent event [{}]", e);
        executor.schedule(()-> applicationEventPublisher.publishEvent(e), 10, TimeUnit.SECONDS);
    }
}
