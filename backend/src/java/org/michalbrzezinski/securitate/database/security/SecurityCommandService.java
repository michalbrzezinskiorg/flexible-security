package org.michalbrzezinski.securitate.database.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.michalbrzezinski.securitate.feature.security.objects.Controller;
import org.michalbrzezinski.securitate.feature.security.objects.Permission;
import org.michalbrzezinski.securitate.feature.security.objects.Role;
import org.michalbrzezinski.securitate.feature.security.objects.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;
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
    private final CustomEntityMapper mapper;

    public void save(Controller payload) {
        log.info("saving payload [{}]", payload);
        ControllerEntity controllerEntity = mapper.map(payload);
        log.info("saving entity [{}]", controllerEntity);
        ControllerEntity save = controllerRepository.save(controllerEntity);
        log.info("saved entity [{}]", save);
    }

    public Permission addPermission(Permission payload) {
        log.info("saving payload [{}]", payload);
        PermissionEntity map = mapper.map(payload);
        log.info("saving entity [{}]", map);
        PermissionEntity save = permissionRepository.save(map);
        log.info("saved entity [{}]", save);
        return mapper.map(save);
    }

    public Optional<Role> addControllerToRole(Role payload) {
        log.info("saving payload [{}]", payload);
        Set<Controller> payloadControllers = payload.getControllers();
        RoleEntity roleEntity = roleRepository.findById(payload.getId())
                .orElse(roleRepository.save(mapper.map(payload)));
        Set<ControllerEntity> controllers = roleEntity.getControllers();
        payloadControllers.stream()
                .map(c -> controllerRepository.findById(c.getId()))
                .filter(Optional::isPresent)
                .forEach(c -> controllers.add(c.get()));
        log.info("saving entity [{}]", roleEntity);
        RoleEntity save = roleRepository.save(roleEntity);
        log.info("saved entity [{}]", save);
        return Optional.ofNullable(save).map(mapper::map);
    }

    public void save(Role payload) {
        log.info("saving payload [{}]", payload);
        RoleEntity roleEntity = roleRepository.findByName(payload.getName()).orElse(mapper.map(payload));
        roleEntity.setControllers(
                payload.getControllers().stream()
                        .flatMap(c -> controllerRepository
                                .findById(c.getId()).stream())
                        .collect(Collectors.toSet())
        );
        log.info("saving entity [{}]", roleEntity);
        RoleEntity save = roleRepository.save(roleEntity);
        log.info("saved entity [{}]", save);
    }

    public void save(User payload) {
        log.info("saving payload [{}]", payload);
        UserEntity userEntity = Optional.ofNullable(payload.getId())
                .map(userRepository::findById)
                .flatMap(a -> a).orElse(mapper.map(payload));
        Set<PermissionEntity> permissionEntities = payload.getPermissions().stream().map(p ->
                permissionRepository.findById(p.getId()).orElse(mapper.map(p))
        ).collect(Collectors.toSet());
        Set<RoleEntity> roleEntities = payload.getRoles().stream().map(r ->
                roleRepository.findByName(r.getName()).orElse(mapper.map(r))
        ).collect(Collectors.toSet());
        userEntity.setPermissions(permissionEntities);
        userEntity.setRoles(roleEntities);
        log.info("saving entity [{}]", userEntity);
        UserEntity save = userRepository.save(userEntity);
        log.info("saved entity [{}]", save);
        payload.getRoles().forEach(this::save);
    }
}