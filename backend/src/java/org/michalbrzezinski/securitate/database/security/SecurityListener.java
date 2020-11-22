package org.michalbrzezinski.securitate.database.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.michalbrzezinski.securitate.domain.security.SecurityEventsPublisher;
import org.michalbrzezinski.securitate.domain.security.events.*;
import org.michalbrzezinski.securitate.domain.security.objects.PermissionDO;
import org.michalbrzezinski.securitate.domain.security.objects.RoleDO;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SecurityListener {

    private final SecurityCommandService securityCommandService;
    private final SecurityEventsPublisher securityEventsPublisher;

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
        PermissionDO p = securityCommandService.addPermission(event.getPayload());
        securityEventsPublisher.publish(PermissionCreatedSystemEvent.builder().payload(p).created(ZonedDateTime.now()).build());
    }

    @Async
    @EventListener
    public void handleAddRoleEvent(EditRoleAddControllerUserEvent event) {
        log.info("#===>  received event [{}]", event);
        Optional<RoleDO> roleDO = securityCommandService.addControllerToRole(event.getPayload());
        roleDO.ifPresent(r -> securityEventsPublisher.publish(EditedRoleControllerAddedSystemEvent.builder().payload(r).created(ZonedDateTime.now()).build()));
    }

    @Async
    @EventListener
    public void handleAddRoleEvent(CreateRoleUserEvent event) {
        log.info("#===>  received event [{}]", event);
        RoleDO roleDO = securityCommandService.saveNewRoleCreatedByUserEvent(event.getPayload());
        securityEventsPublisher.publish(RoleCreatedSystemEvent.builder().payload(roleDO).created(ZonedDateTime.now()).build());
    }


    @Async
    @EventListener
    public void handleAddRoleEvent(Object event) {
        log.info("#===>  received Object event [{}]", event);
    }
}
