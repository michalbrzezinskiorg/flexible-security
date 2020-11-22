package org.michalbrzezinski.securitate.config.security;

import org.michalbrzezinski.securitate.domain.security.objects.ControllerDO;
import org.michalbrzezinski.securitate.domain.security.objects.RoleDO;
import org.michalbrzezinski.securitate.domain.security.objects.UserDO;

import java.util.Optional;
import java.util.Set;

public interface SecurityServiceForConfiguration {
    Set<ControllerDO> findAllControllers();

    Optional<UserDO> getByLogin(String login);

    Optional<RoleDO> findRoleByName(String rolename);

    long countAll();

    Iterable<ControllerDO> findControllersByUser(UserDO user);
}
