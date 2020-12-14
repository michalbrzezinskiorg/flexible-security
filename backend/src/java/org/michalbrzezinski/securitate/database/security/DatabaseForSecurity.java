package org.michalbrzezinski.securitate.database.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.michalbrzezinski.securitate.config.security.port.DatabaseForSecurityConfiguration;
import org.michalbrzezinski.securitate.feature.security.DatabaseForSecurityManagement;
import org.michalbrzezinski.securitate.feature.security.objects.Controller;
import org.michalbrzezinski.securitate.feature.security.objects.Permission;
import org.michalbrzezinski.securitate.feature.security.objects.Role;
import org.michalbrzezinski.securitate.feature.security.objects.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
class DatabaseForSecurity implements DatabaseForSecurityConfiguration, DatabaseForSecurityManagement {

    private final ControllerRepository controllerRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final CustomEntityMapper mapper;

    @Override
    public Set<Controller> findAllControllers() {
        log.info("findAllControllers");
        Set<ControllerEntity> all = controllerRepository.findAll();
        return all.stream()
                .map(c -> mapper.map(c))
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<Role> findRoleByName(String roleName) {
        log.info("findRoleByName [{}]", roleName);
        return roleRepository.findByName(roleName).map(a -> mapper.map(a));
    }

    @Override
    public Optional<User> getByLogin(String login) {
        log.info("getByLogin [{}]", login);
        Optional<UserEntity> byLogin = userRepository.findByLogin(login);
        return byLogin.map(mapper::deepMap);
    }

    @Override
    public Set<Controller> findControllersByUser(User user) {
        log.info("findControllersByUser [{}]", user);
        Set<Controller> controllers = userRepository.findByLogin(user.getLogin())
                .map(u -> getControllerEntities(u))
                .stream()
                .flatMap(a -> a.stream())
                .map(c -> mapper.map(c))
                .collect(Collectors.toSet());
        log.info("found Controllers [{}]", controllers);
        return controllers;
    }

    private Set<ControllerEntity> getControllerEntities(UserEntity u) {
        Set<ControllerEntity> c1 = u.getPermissions()
                .stream()
                .flatMap(p -> p.getControllers().stream())
                .collect(Collectors.toSet());
        Set<ControllerEntity> c2 = u.getRoles().stream()
                .flatMap(r -> r.getControllers().stream())
                .collect(Collectors.toSet());
        c1.addAll(c2);
        return c1;
    }

    @Override
    public long countAll() {
        log.info("countAll users ");
        return userRepository.count();
    }

    @Override
    public Optional<User> getUser(String login) {
        return userRepository.findByLogin(login).map(mapper::map);
    }

    @Override
    public Page<Role> getAllRoles(Pageable pageable) {
        return roleRepository.findAll(pageable).map(mapper::map);
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(mapper::map);
    }

    @Override
    public Page<Permission> getAllPermissions(Pageable pageable) {
        return permissionRepository.findAll(pageable).map(mapper::map);
    }

    @Override
    public Page<Controller> getAllControllers(Pageable pageable) {
        return controllerRepository.findAll(pageable).map(mapper::map);
    }

    @Override
    public Optional<Role> getRoleById(Integer id) {
        return roleRepository.findById(id).map(mapper::map);
    }

    @Override
    public List<Controller> getControllersByIds(List<Integer> collect) {
        return controllerRepository.findByIdIn(collect).stream()
                .map(mapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> getUserByLogin(String login) {
        return userRepository.findByLogin(login).map(mapper::map);
    }
}
