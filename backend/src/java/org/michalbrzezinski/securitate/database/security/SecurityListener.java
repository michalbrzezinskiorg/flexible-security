package org.michalbrzezinski.securitate.database.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.michalbrzezinski.securitate.config.security.events.AddControllerEvent;
import org.michalbrzezinski.securitate.config.security.events.AddRoleEvent;
import org.michalbrzezinski.securitate.config.security.events.AddUserEvent;
import org.michalbrzezinski.securitate.feature.security.AddPermissionEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SecurityListener {

    private final SecurityCommandService securityCommandService;

    @Async
    @EventListener
    public void addAddControllerEvent(AddControllerEvent event) {
        log.info("#===>  received event [{}]", event);
        securityCommandService.save(event.getPayload());
    }

    @Async
    @EventListener
    public void handleAddUserEvent(AddUserEvent event) {
        log.info("#===> received event [{}]", event);
        securityCommandService.save(event.getPayload());
    }

    @Async
    @EventListener
    public void handleAddRoleEvent(AddRoleEvent event) {
        log.info("#===>  received event [{}]", event);
        securityCommandService.save(event.getPayload());
    }

    @Async
    @EventListener
    public void handleAddRoleEvent(AddPermissionEvent event) {
        log.info("#===>  received event [{}]", event);
        securityCommandService.addPermission(event.getPayload());
    }


}
