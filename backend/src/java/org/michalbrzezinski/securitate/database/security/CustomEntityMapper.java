package org.michalbrzezinski.securitate.database.security;

import org.michalbrzezinski.securitate.domain.security.ControllerDO;
import org.michalbrzezinski.securitate.domain.security.PermissionDO;
import org.michalbrzezinski.securitate.domain.security.RoleDO;
import org.michalbrzezinski.securitate.domain.security.UserDO;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
class CustomEntityMapper {

    Role buildRole(RoleDO role) {
        return Role.builder()
                .id(role.getId())
                .name(role.getName())
                .active(role.getActive())
                .build();
    }

    RoleDO buildRole(Role role) {
        return RoleDO.builder()
                .id(role.getId())
                .active(role.getActive())
                .name(role.getName())
                .build();
    }

    User buildUser(UserDO user) {
        return User.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .login(user.getLogin())
                .active(user.isActive())
                .build();
    }

    UserDO buildUser(User user) {
        return UserDO.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .login(user.getLogin())
                .active(user.isActive())
                .build();
    }

    Permission buildPermissions(PermissionDO r) {
        return Permission.builder()
                .id(r.getId())
                .active(r.isActive())
                .build();
    }

    ControllerDO buildController(Controller c) {
        return ControllerDO.builder()
                .id(c.getId())
                .active(c.isActive())
                .http(c.getHttp())
                .method(c.getMethod())
                .controller(c.getController())
                .id(c.getId())
                .build();
    }

    Controller buildController(ControllerDO c) {
        return Controller.builder()
                .id(c.getId())
                .active(c.isActive())
                .http(c.getHttp())
                .method(c.getMethod())
                .controller(c.getController())
                .id(c.getId())
                .build();
    }

    public Permission createPermissionForUser(PermissionDO permission, User createdBy, User createdFor, Collection<Controller> controllers) {
        return Permission.builder()
                .createdBy(createdBy)
                .controllers(controllers)
                .fromDate(permission.getFromDate())
                .active(permission.isActive())
                .toDate(permission.getToDate())
                .build();
    }
}
