package org.michalbrzezinski.securitate.database.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.michalbrzezinski.securitate.feature.security.events.*;
import org.michalbrzezinski.securitate.feature.security.objects.Permission;
import org.michalbrzezinski.securitate.feature.security.objects.Role;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
class SecurityListener {

    private final SecurityCommandService securityCommandService;
    private final SecurityDatabseEventsPublisher securityEventsPublisher;

    @Async
    @EventListener
    public void addAddControllerEvent(CreateControllerSystemEvent event) {
        log.info("#===>  received event [{}]", event);
        securityCommandService.save(event.getPayload());
    }

    @Async
    @EventListener
    public void handleAddUserEvent(CreateUserSystemEvent event) {
        log.info("#===> received event [{}]", event);
        securityCommandService.save(event.getPayload());
    }

    @Async
    @EventListener
    public void handleAddRoleEvent(CreateRoleSystemEvent event) {
        log.info("#===>  received event [{}]", event);
        securityCommandService.save(event.getPayload());
    }

    @Async
    @EventListener
    public void handleAddRoleEvent(CreatePermissionUserEvent event) {
        log.info("#===>  received event [{}]", event);
        Permission p = securityCommandService.addPermission(event.getPayload());
        securityEventsPublisher.publish(PermissionCreatedSystemEvent.builder().payload(p).created(ZonedDateTime.now()).build());
    }

    @Async
    @EventListener
    public void handleAddRoleEvent(EditRoleAddControllerUserEvent event) {
        log.info("#===>  received event [{}]", event);
        Optional<Role> roleDO = securityCommandService.addControllerToRole(event.getPayload());
        roleDO.ifPresent(r -> securityEventsPublisher.publish(EditedRoleControllerAddedSystemEvent.builder().payload(r).created(ZonedDateTime.now()).build()));
    }

    @Async
    @EventListener
    public void handleAddRoleEvent(CreateRoleUserEvent event) {
        log.info("#===>  received event [{}]", event);
        Role role = securityCommandService.saveNewRoleCreatedByUserEvent(event.getPayload());
        securityEventsPublisher.publish(RoleCreatedSystemEvent.builder().payload(role).created(ZonedDateTime.now()).build());
    }


    @Async
    @EventListener
    public void handleAddRoleEvent(Object event) {
        log.info("#===>  received Object event [{}]", event);
    }
}
