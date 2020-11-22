package org.michalbrzezinski.securitate.feature.security;

import lombok.RequiredArgsConstructor;
import org.michalbrzezinski.securitate.domain.security.SecurityEventsPublisher;
import org.michalbrzezinski.securitate.domain.security.events.CreatePermissionUserEvent;
import org.michalbrzezinski.securitate.domain.security.events.CreateRoleUserEvent;
import org.michalbrzezinski.securitate.domain.security.events.EditRoleAddControllerUserEvent;
import org.michalbrzezinski.securitate.domain.security.objects.ControllerDO;
import org.michalbrzezinski.securitate.domain.security.objects.PermissionDO;
import org.michalbrzezinski.securitate.domain.security.objects.RoleDO;
import org.michalbrzezinski.securitate.domain.security.objects.UserDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class SecurityManegementService {

    private final SecurityServiceForManagemet securityService;
    private final SecurityEventsPublisher publisher;

    public Page<RoleDO> getAllRoles(Pageable pageable) {
        return securityService.getAllRoles(pageable);
    }

    public Page<UserDO> getAllUsers(Pageable pageable) {
        return securityService.getAllUsers(pageable);
    }

    public Page<PermissionDO> getAllPermissions(Pageable pageable) {
        return securityService.getAllPermissions(pageable);
    }

    public Page<ControllerDO> getAllControllers(Pageable pageable) {
        return securityService.getAllControllers(pageable);
    }

    public void addPermission(PermissionDO permissionDO) {
        Optional<UserDO> permissionFor = securityService.getUser(permissionDO.getPermissionFor().getId());
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<UserDO> permissionBy = securityService.getUser(Integer.parseInt(userId));
        permissionFor.flatMap(
                forUser -> permissionBy.map(byUser ->
                        PermissionDO.builder()
                                .permissionFor(forUser)
                                .createdBy(byUser)
                                .controllers(permissionDO.getControllers())
                                .fromDate(Optional.ofNullable(permissionDO.getFromDate()).orElse(ZonedDateTime.now()))
                                .toDate(Optional.ofNullable(permissionDO.getToDate()).orElse(ZonedDateTime.now().plusMonths(1)))
                                .build()))
                .ifPresent(p ->
                        publisher.publish(CreatePermissionUserEvent
                                .builder()
                                .payload(p)
                                .created(ZonedDateTime.now())
                                .build()));
    }

    public void addControllerToRole(RoleDO roleDO) {
        Optional<RoleDO> role = securityService.getRoleById(roleDO.getId());
        List<ControllerDO> controllers = securityService
                .getControllersByIds(roleDO.getControllers()
                        .stream()
                        .map(ControllerDO::getId)
                        .collect(Collectors.toList()));

        role.map(r -> RoleDO.builder()
                .id(r.getId())
                .controllers(controllers)
                .build())
                .ifPresent(r -> publisher.publish(
                        EditRoleAddControllerUserEvent.builder()
                                .payload(r).created(ZonedDateTime.now())
                                .build()));
    }

    public void createRole(RoleDO roleDO) {
        publisher.publish(CreateRoleUserEvent.builder()
                .payload(roleDO)
                .created(ZonedDateTime.now())
                .build());
    }

    public void addRoleToUser(RoleDO roleDO) {

    }
}
