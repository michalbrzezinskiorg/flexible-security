package org.michalbrzezinski.securitate.feature.security;

import org.michalbrzezinski.securitate.feature.security.objects.Controller;
import org.michalbrzezinski.securitate.feature.security.objects.Permission;
import org.michalbrzezinski.securitate.feature.security.objects.Role;
import org.michalbrzezinski.securitate.feature.security.objects.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface DatabaseForSecurityManagement {
    Optional<User> getUser(String id);

    Page<Role> getAllRoles(Pageable pageable);

    Page<User> getAllUsers(Pageable pageable);

    Page<Permission> getAllPermissions(Pageable pageable);

    Page<Controller> getAllControllers(Pageable pageable);

    Optional<Role> getRoleById(Integer id);

    List<Controller> getControllersByIds(List<Integer> collect);

    Optional<User> getUserByLogin(String login);
}
