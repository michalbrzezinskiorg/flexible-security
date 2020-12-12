package org.michalbrzezinski.securitate.config.security.objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import org.michalbrzezinski.securitate.feature.security.objects.Permission;
import org.michalbrzezinski.securitate.feature.security.objects.Role;

import java.util.Set;

@Value
@Builder
@AllArgsConstructor
public class Actor {
    Integer id;
    String name;
    String surname;
    String login;
    boolean active;
    @Singular
    Set<Permission> permissions;
    @Singular
    Set<Role> roles;
}
