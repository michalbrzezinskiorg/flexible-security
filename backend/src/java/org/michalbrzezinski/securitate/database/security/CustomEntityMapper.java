package org.michalbrzezinski.securitate.database.security;

import org.michalbrzezinski.securitate.feature.security.objects.Controller;
import org.michalbrzezinski.securitate.feature.security.objects.Permission;
import org.michalbrzezinski.securitate.feature.security.objects.Role;
import org.michalbrzezinski.securitate.feature.security.objects.User;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Component
class CustomEntityMapper {

    RoleEntity map(Role role) {
        return RoleEntity.builder()
                .id(role.getId())
                .name(role.getName())
                .active(role.getActive())
                .build();
    }

    Role map(RoleEntity roleEntity) {
        return Role.builder()
                .id(roleEntity.getId())
                .active(roleEntity.getActive())
                .name(roleEntity.getName())
                .build();
    }

    UserEntity map(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .login(user.getLogin())
                .active(user.isActive())
                .build();
    }

    User map(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .surname(userEntity.getSurname())
                .login(userEntity.getLogin())
                .active(userEntity.isActive())
                .build();
    }

    PermissionEntity map(Permission r) {
        return PermissionEntity.builder()
                .id(r.getId())
                .active(r.isActive())
                .build();
    }

    Controller map(ControllerEntity c) {
        return Controller.builder()
                .id(c.getId())
                .active(c.isActive())
                .http(c.getHttp())
                .method(c.getMethod())
                .controller(c.getController())
                .id(c.getId())
                .build();
    }

    ControllerEntity map(Controller c) {
        return ControllerEntity.builder()
                .id(c.getId())
                .active(c.isActive())
                .http(c.getHttp())
                .method(c.getMethod())
                .controller(c.getController())
                .id(c.getId())
                .build();
    }

    public PermissionEntity map(Permission permission, UserEntity createdBy, UserEntity createdFor, Collection<ControllerEntity> controllers) {
        return PermissionEntity.builder()
                .createdBy(createdBy.getLogin())
                .controllers(controllers)
                .fromDate(permission.getFromDate())
                .active(permission.isActive())
                .toDate(permission.getToDate())
                .build();
    }

    public Permission map(PermissionEntity p) {
        return Permission.builder()
                .fromDate(p.getFromDate())
                .toDate(p.getToDate())
                .createdBy(p.getCreatedBy())
                .id(p.getId())
                .controllers(map(p.getControllers()))
                .active(p.isActive())
                .build();
    }

    private Collection<? extends Controller> map(Set<ControllerEntity> controllers) {
        return controllers.stream().map(this::map).collect(Collectors.toList());
    }

    public User deepMap(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .active(userEntity.isActive())
                .name(userEntity.getName())
                .login(userEntity.getLogin())
                .roles(userEntity.getRoles().stream().map(this::deepMap).collect(Collectors.toSet()))
                .permissions(userEntity.getPermissions().stream().map(this::deepMap).collect(Collectors.toSet()))
                .build();
    }

    private Permission deepMap(PermissionEntity permissionEntity) {
        return Permission.builder()
                .id(permissionEntity.getId())
                .active(permissionEntity.isActive())
                .controllers(permissionEntity.getControllers().stream().map(this::map).collect(Collectors.toSet()))
                .build();
    }

    private Role deepMap(RoleEntity roleEntity) {
        return Role.builder()
                .id(roleEntity.getId())
                .active(roleEntity.getActive())
                .controllers(roleEntity.getControllers().stream().map(this::map).collect(Collectors.toSet()))
                .build();
    }
}
