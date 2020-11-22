package org.michalbrzezinski.securitate.database.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.michalbrzezinski.securitate.config.security.SecurityServiceForConfiguration;
import org.michalbrzezinski.securitate.domain.security.objects.ControllerDO;
import org.michalbrzezinski.securitate.domain.security.objects.PermissionDO;
import org.michalbrzezinski.securitate.domain.security.objects.RoleDO;
import org.michalbrzezinski.securitate.domain.security.objects.UserDO;
import org.michalbrzezinski.securitate.feature.security.SecurityServiceForManagemet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
class SecurityQueryService implements SecurityServiceForConfiguration, SecurityServiceForManagemet {

    private final ControllerRepository controllerRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final CustomEntityMapper customEntityMapper;

    @Override
    public Set<ControllerDO> findAllControllers() {
        log.info("findAllControllers");
        Set<Controller> all = controllerRepository.findAll();
        return all.stream()
                .map(c -> customEntityMapper.map(c))
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<RoleDO> findRoleByName(String roleName) {
        log.info("findRoleByName [{}]", roleName);
        return roleRepository.findByName(roleName).map(a -> customEntityMapper.map(a));
    }

    @Override
    public Optional<UserDO> getByLogin(String login) {
        log.info("getByLogin [{}]", login);
        Optional<User> byLogin = userRepository.findByLogin(login);
        if (byLogin.isEmpty()) return Optional.empty();
        return Optional.ofNullable(customEntityMapper.map(byLogin.get()));
    }

    @Override
    public Set<ControllerDO> findControllersByUser(UserDO user) {
        log.info("findControllersByUser [{}]", user);
        Set<ControllerDO> controllerDOS = controllerRepository.findByUser(user.getId())
                .stream()
                .map(c -> customEntityMapper.map(c))
                .collect(Collectors.toSet());
        log.info("found Controllers [{}]", controllerDOS);
        return controllerDOS;
    }

    @Override
    public long countAll() {
        log.info("countAll users ");
        return userRepository.count();
    }

    @Override
    public Optional<UserDO> getUser(Integer id) {
        return userRepository.findById(id).map(customEntityMapper::map);
    }

    @Override
    public Page<RoleDO> getAllRoles(Pageable pageable) {
        return roleRepository.findAll(pageable).map(customEntityMapper::map);
    }

    @Override
    public Page<UserDO> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(customEntityMapper::map);
    }

    @Override
    public Page<PermissionDO> getAllPermissions(Pageable pageable) {
        return permissionRepository.findAll(pageable).map(customEntityMapper::map);
    }

    @Override
    public Page<ControllerDO> getAllControllers(Pageable pageable) {
        return controllerRepository.findAll(pageable).map(customEntityMapper::map);
    }

    @Override
    public Optional<RoleDO> getRoleById(Integer id) {
        return roleRepository.findById(id).map(customEntityMapper::map);
    }

    @Override
    public List<ControllerDO> getControllersByIds(List<Integer> collect) {
        return controllerRepository.findByIdIn(collect).stream()
                .map(customEntityMapper::map)
                .collect(Collectors.toList());
    }
}
