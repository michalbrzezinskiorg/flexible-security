package org.michalbrzezinski.securitate.domain.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.Set;


@Value
@Builder
@AllArgsConstructor
public class ControllerDO {
    Integer id;
    String controller;
    String method;
    String http;
    Set<RoleDO> roles;
    Set<PermissionDO> permissions;
    boolean active;
}
