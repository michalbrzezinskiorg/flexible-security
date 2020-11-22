package org.michalbrzezinski.securitate.database.security;

import org.michalbrzezinski.securitate.domain.security.objects.ControllerDO;
import org.michalbrzezinski.securitate.domain.security.objects.PermissionDO;
import org.michalbrzezinski.securitate.domain.security.objects.RoleDO;
import org.michalbrzezinski.securitate.domain.security.objects.UserDO;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Component
class CustomEntityMapper {

    Role map(RoleDO role) {
        return Role.builder()
                .id(role.getId())
                .name(role.getName())
                .active(role.getActive())
                .build();
    }

    RoleDO map(Role role) {
        return RoleDO.builder()
                .id(role.getId())
                .active(role.getActive())
                .name(role.getName())
                .build();
    }

    User map(UserDO user) {
        return User.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .login(user.getLogin())
                .active(user.isActive())
                .build();
    }

    UserDO map(User user) {
        return UserDO.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .login(user.getLogin())
                .active(user.isActive())
                .build();
    }

    Permission map(PermissionDO r) {
        return Permission.builder()
                .id(r.getId())
                .active(r.isActive())
                .build();
    }

    ControllerDO map(Controller c) {
        return ControllerDO.builder()
                .id(c.getId())
                .active(c.isActive())
                .http(c.getHttp())
                .method(c.getMethod())
                .controller(c.getController())
                .id(c.getId())
                .build();
    }

    Controller map(ControllerDO c) {
        return Controller.builder()
                .id(c.getId())
                .active(c.isActive())
                .http(c.getHttp())
                .method(c.getMethod())
                .controller(c.getController())
                .id(c.getId())
                .build();
    }

    public Permission map(PermissionDO permission, User createdBy, User createdFor, Collection<Controller> controllers) {
        return Permission.builder()
                .createdBy(createdBy)
                .controllers(controllers)
                .fromDate(permission.getFromDate())
                .active(permission.isActive())
                .toDate(permission.getToDate())
                .build();
    }

    public PermissionDO map(Permission p) {
        return PermissionDO.builder()
                .fromDate(p.getFromDate())
                .toDate(p.getToDate())
                .createdBy(map(p.getCreatedBy()))
                .id(p.getId())
                .controllers(map(p.getControllers()))
                .active(p.isActive())
                .build();
    }

    private Collection<? extends ControllerDO> map(Set<Controller> controllers) {
        return controllers.stream().map(this::map).collect(Collectors.toList());
    }
}
