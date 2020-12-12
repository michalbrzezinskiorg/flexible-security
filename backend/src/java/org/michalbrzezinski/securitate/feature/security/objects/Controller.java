package org.michalbrzezinski.securitate.feature.security.objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.Set;


@Value
@Builder
@AllArgsConstructor
public class Controller {
    Integer id;
    String controller;
    String method;
    String http;
    Set<Role> roles;
    Set<Permission> permissions;
    boolean active;
}
