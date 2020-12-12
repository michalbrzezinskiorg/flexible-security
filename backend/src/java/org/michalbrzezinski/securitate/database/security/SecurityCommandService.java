package org.michalbrzezinski.securitate.database.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.michalbrzezinski.securitate.feature.security.objects.Controller;
import org.michalbrzezinski.securitate.feature.security.objects.Permission;
import org.michalbrzezinski.securitate.feature.security.objects.Role;
import org.michalbrzezinski.securitate.feature.security.objects.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
class SecurityCommandService {

    private final ControllerRepository controllerRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final CustomEntityMapper customEntityMapper;


    void save(Controller controller) {
        log.info("save [{}]", controller);
        try {
            log.info("found controller [{}]", controller);
            ControllerEntity controllerEntity = customEntityMapper.map(controller);
            log.info("mapped controller [{}]", controllerEntity);
            controllerRepository.save(controllerEntity);
        } catch (Exception e) {
            log.warn("found the same controller several times - NO ISSUE [{}]", e);
        }
    }

    User save(User user) {
        log.info("save [{}]", user);
        UserEntity u = customEntityMapper.map(user);
        saveRoles(user.getRoles());
        savePermissionDO(user.getPermissions());
        Set<RoleEntity> role = getRoles(user);
        Set<PermissionEntity> permission = getPermissions(user);
        u.setRoles(role);
        u.setPermissions(permission);
        return customEntityMapper.map(userRepository.save(u));
    }

    Role save(Role role) {
        log.info("save [{}]", role);
        RoleEntity r = customEntityMapper.map(role);
        Set<ControllerEntity> roles = getControllersForRole(role);
        r.setControllers(roles);
        return customEntityMapper.map(save(r));
    }


    private Set<RoleEntity> getRoles(User u) {
        log.info("getRoles for user [{}]", u);
        Set<RoleEntity> role = u.getRoles().stream()
                .map(r -> roleRepository.findById(r.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        log.info("found Roles [{}]", role);
        return role;
    }

    private Set<PermissionEntity> getPermissions(User u) {
        log.info("getPermissions for user [{}]", u);
        Set<PermissionEntity> permission = u.getPermissions()
                .stream().map(p -> permissionRepository.findById(p.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        log.info("found Permissions [{}]", permission);
        return permission;
    }


    private Set<ControllerEntity> getControllersForRole(Role role) {
        log.info("getControllersForRole [{}]", role);
        Set<ControllerEntity> controller = role.getControllers().stream()
                .map(c -> controllerRepository.findById(c.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        log.info("found Controllers [{}]", controller);
        return controller;
    }

    private void savePermissionDO(Set<Permission> permissions) {
        log.info("savePermissionDO [{}]", permissions);
        Set<PermissionEntity> perms = permissions.stream()
                .map(p -> save(p))
                .collect(Collectors.toSet());
        log.info("permissions saved [{}]", perms);
    }

    private PermissionEntity save(Permission p) {
        log.info("save [{}]", p);
        PermissionEntity saved = permissionRepository.save(customEntityMapper.map(p));
        log.info("saved [{}]", saved);
        return saved;
    }

    private void saveRoles(Set<Role> roles) {
        log.info("save [{}]", roles);
        roles.stream()
                .filter(r -> roleRepository.findByName(r.getName()).isEmpty())
                .map(r -> save(customEntityMapper.map(r)))
                .peek(r -> r.setControllers(getControllers(r)))
                .collect(Collectors.toSet());
    }

    private Set<ControllerEntity> getControllers(RoleEntity r) {
        log.info("save [{}]", r);
        return r.getControllers().stream()
                .map(c -> controllerRepository.findById(c.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }

    private RoleEntity save(RoleEntity roleEntity) {
        log.info("save [{}]", roleEntity);
        return roleRepository.save(roleEntity);
    }

    public Permission addPermission(Permission permission) {
        log.info("creating permission [{}]", permission);
        Collection<ControllerEntity> controller = controllerRepository.findByIdIn(permission.getControllers().stream().map(Controller::getId).collect(Collectors.toList()));
        UserEntity createdFor = userRepository.getOne(permission.getPermissionFor().getId());
        UserEntity createdBy = userRepository.getOne(permission.getCreatedBy().getId());
        PermissionEntity permissionEntityForUser = customEntityMapper.map(permission, createdBy, createdFor, controller);
        log.info("saving mapped values stored in PermissionDO [{}]", permissionEntityForUser);
        return customEntityMapper.map(permissionRepository.save(permissionEntityForUser));
    }

    public Optional<Role> addControllerToRole(Role roleDO) {
        Optional<RoleEntity> role = roleRepository.findById(roleDO.getId());
        role.ifPresent(r -> addControllerToRole(r, roleDO.getControllers()));
        Optional<Role> result = role.map(customEntityMapper::map);
        return result;
    }

    private void addControllerToRole(RoleEntity r, Set<Controller> controllersDO) {
        List<ControllerEntity> controller = controllerRepository.findByIdIn(controllersDO.stream().map(c -> c.getId()).collect(Collectors.toList()));
        r.setControllers(new HashSet<>(controller));
        roleRepository.save(r);
    }

    public Role saveNewRoleCreatedByUserEvent(Role role) {
        return customEntityMapper.map(roleRepository.save(customEntityMapper.map(role)));
    }
}