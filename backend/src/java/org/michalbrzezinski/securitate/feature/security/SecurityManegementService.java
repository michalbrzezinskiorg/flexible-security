package org.michalbrzezinski.securitate.feature.security;

import lombok.RequiredArgsConstructor;
import org.michalbrzezinski.securitate.feature.security.events.CreatePermissionUserEvent;
import org.michalbrzezinski.securitate.feature.security.events.CreateRoleUserEvent;
import org.michalbrzezinski.securitate.feature.security.events.EditRoleAddControllerUserEvent;
import org.michalbrzezinski.securitate.feature.security.objects.Controller;
import org.michalbrzezinski.securitate.feature.security.objects.Permission;
import org.michalbrzezinski.securitate.feature.security.objects.Role;
import org.michalbrzezinski.securitate.feature.security.objects.User;
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

    private final DatabaseForSecurityManagement securityService;
    private final SecurityManagementEventsPublisher publisher;

    public Page<Role> getAllRoles(Pageable pageable) {
        return securityService.getAllRoles(pageable);
    }

    public Page<User> getAllUsers(Pageable pageable) {
        return securityService.getAllUsers(pageable);
    }

    public Page<Permission> getAllPermissions(Pageable pageable) {
        return securityService.getAllPermissions(pageable);
    }

    public Page<Controller> getAllControllers(Pageable pageable) {
        return securityService.getAllControllers(pageable);
    }

    public void addPermission(Permission permission) {
        Optional<User> permissionFor = securityService.getUser(permission.getPermissionFor().getId());
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> permissionBy = securityService.getUser(Integer.parseInt(userId));
        permissionFor.flatMap(
                forUser -> permissionBy.map(byUser ->
                        Permission.builder()
                                .permissionFor(forUser)
                                .createdBy(byUser)
                                .controllers(permission.getControllers())
                                .fromDate(Optional.ofNullable(permission.getFromDate()).orElse(ZonedDateTime.now()))
                                .toDate(Optional.ofNullable(permission.getToDate()).orElse(ZonedDateTime.now().plusMonths(1)))
                                .build()))
                .ifPresent(p ->
                        publisher.publish(CreatePermissionUserEvent
                                .builder()
                                .payload(p)
                                .created(ZonedDateTime.now())
                                .build()));
    }

    public void addControllerToRole(Role roleDO) {
        Optional<Role> role = securityService.getRoleById(roleDO.getId());
        List<Controller> controllers = securityService
                .getControllersByIds(roleDO.getControllers()
                        .stream()
                        .map(Controller::getId)
                        .collect(Collectors.toList()));

        role.map(r -> Role.builder()
                .id(r.getId())
                .controllers(controllers)
                .build())
                .ifPresent(r -> publisher.publish(
                        EditRoleAddControllerUserEvent.builder()
                                .payload(r).created(ZonedDateTime.now())
                                .build()));
    }

    public void createRole(Role role) {
        publisher.publish(CreateRoleUserEvent.builder()
                .payload(role)
                .created(ZonedDateTime.now())
                .build());
    }

    public void addRoleToUser(Role role) {

    }
}
