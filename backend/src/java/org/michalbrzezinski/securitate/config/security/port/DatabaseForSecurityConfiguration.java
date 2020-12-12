package org.michalbrzezinski.securitate.config.security.port;

import org.michalbrzezinski.securitate.feature.security.objects.Controller;
import org.michalbrzezinski.securitate.feature.security.objects.Role;
import org.michalbrzezinski.securitate.feature.security.objects.User;

import java.util.Optional;
import java.util.Set;

public interface DatabaseForSecurityConfiguration {
    Set<Controller> findAllControllers();

    Optional<User> getByLogin(String login);

    Optional<Role> findRoleByName(String rolename);

    long countAll();

    Iterable<Controller> findControllersByUser(User user);
}
