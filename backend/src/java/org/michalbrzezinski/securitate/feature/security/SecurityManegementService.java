package org.michalbrzezinski.securitate.feature.security;

import lombok.RequiredArgsConstructor;
import org.michalbrzezinski.securitate.config.SecuritateEventsPublisher;
import org.michalbrzezinski.securitate.domain.security.ControllerDO;
import org.michalbrzezinski.securitate.domain.security.PermissionDO;
import org.michalbrzezinski.securitate.domain.security.RoleDO;
import org.michalbrzezinski.securitate.domain.security.UserDO;
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

    public static final String userId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    private final SecurityServiceForManagemet securityService;
    private final SecuritateEventsPublisher publisher;

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
        UserDO permissionFor = securityService.getUser(permissionDO.getPermissionFor().getId());
        UserDO permissionBy = securityService.getUser(Integer.parseInt(userId));
        PermissionDO permission = PermissionDO.builder()
                .permissionFor(permissionFor)
                .createdBy(permissionBy)
                .controllers(permissionDO.getControllers())
                .fromDate(Optional.ofNullable(permissionDO.getFromDate()).orElse(ZonedDateTime.now()))
                .toDate(Optional.ofNullable(permissionDO.getToDate()).orElse(ZonedDateTime.now().plusMonths(1)))
                .build();
        publisher.publish(AddPermissionEvent
                .builder()
                .payload(permission)
                .created(ZonedDateTime.now())
                .build());
    }

    public void addControllerToRole(RoleDO roleDO) {
        RoleDO role = securityService.getRoleById(roleDO.getId());
        List<ControllerDO> controllers = securityService
                .getControllersById(
                        roleDO.getControllers()
                                .stream()
                                .map(ControllerDO::getId)
                                .collect(Collectors.toList()));
        RoleDO roleChanged = RoleDO.builder()
                .id(role.getId())
                .controllers(controllers)
                .build();
        publisher.publish(AddControllerToRole.builder().payload(roleChanged).created(ZonedDateTime.now()).build());

    }

    public void createRole(RoleDO roleDO) {


    }

    public void addRoleToUser(RoleDO roleDO) {

    }
}
