package org.michalbrzezinski.securitate.database.security;

import org.michalbrzezinski.securitate.config.security.SecurityServiceForConfiguration;
import org.michalbrzezinski.securitate.domain.security.ControllerDO;
import org.michalbrzezinski.securitate.domain.security.RoleDO;
import org.michalbrzezinski.securitate.domain.security.UserDO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.michalbrzezinski.securitate.feature.security.SecurityServiceForManagemet;
import org.springframework.stereotype.Service;

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

    public Set<ControllerDO> findAllControllers() {
        log.info("findAllControllers");
        Set<Controller> all = controllerRepository.findAll();
        return all.stream()
                .map(c -> customEntityMapper.buildController(c))
                .collect(Collectors.toSet());
    }


    public Optional<RoleDO> findRoleByName(String roleName) {
        log.info("findRoleByName [{}]", roleName);
        return roleRepository.findByName(roleName).map(a -> customEntityMapper.buildRole(a));
    }

    public Optional<UserDO> getByLogin(String login) {
        log.info("getByLogin [{}]", login);
        Optional<User> byLogin = userRepository.findByLogin(login);
        if (byLogin.isEmpty()) return Optional.empty();
        return Optional.ofNullable(customEntityMapper.buildUser(byLogin.get()));
    }

    public Set<ControllerDO> findControllersByUser(UserDO user) {
        log.info("findControllersByUser [{}]", user);
        Set<ControllerDO> controllerDOS = controllerRepository.findByUser(user.getId())
                .stream()
                .map(c -> customEntityMapper.buildController(c))
                .collect(Collectors.toSet());
        log.info("found Controllers [{}]", controllerDOS);
        return controllerDOS;
    }

    public long countAll() {
        log.info("countAll users ");
        return userRepository.count();
    }
}
