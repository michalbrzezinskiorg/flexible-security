package org.michalbrzezinski.securitate.config.security.port;

import org.michalbrzezinski.securitate.feature.security.objects.ControllerDO;
import org.michalbrzezinski.securitate.feature.security.objects.RoleDO;
import org.michalbrzezinski.securitate.feature.security.objects.UserDO;

import java.util.Optional;
import java.util.Set;

public interface DatabaseForSecurityConfiguration {
    Set<ControllerDO> findAllControllers();

    Optional<UserDO> getByLogin(String login);

    Optional<RoleDO> findRoleByName(String rolename);

    long countAll();

    Iterable<ControllerDO> findControllersByUser(UserDO user);
}
