package org.michalbrzezinski.securitate.feature.security;

import lombok.RequiredArgsConstructor;
import org.michalbrzezinski.securitate.feature.security.objects.Controller;
import org.michalbrzezinski.securitate.feature.security.objects.Permission;
import org.michalbrzezinski.securitate.feature.security.objects.Role;
import org.michalbrzezinski.securitate.feature.security.objects.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("security")
class SecurityManagementController {

    private final SecurityManegementService securityManagementService;

    @GetMapping("roles")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public Page<Role> getAllRoles(Pageable pageable) {
        return securityManagementService.getAllRoles(pageable);
    }

    @GetMapping("users")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public Page<User> getAllUsers(Pageable pageable) {
        return securityManagementService.getAllUsers(pageable);
    }

    @GetMapping("controllers")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public Page<Controller> getAllControllers(Pageable pageable) {
        return securityManagementService.getAllControllers(pageable);
    }

    @GetMapping("permissions")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public Page<Permission> getAllPermissions(Pageable pageable) {
        return securityManagementService.getAllPermissions(pageable);
    }

    @PostMapping("user/permission")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void addPermission(Permission permission) {
        securityManagementService.addPermission(permission);
    }

    @PostMapping("role/controller")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void addControllerToRole(Role role) {
        securityManagementService.addControllerToRole(role);
    }

    @PostMapping("user/role")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void createRole(Role role) {
        securityManagementService.createRole(role);
    }

    @PostMapping("role")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void addRoleToUser(Role role) {
        securityManagementService.addRoleToUser(role);
    }
}
