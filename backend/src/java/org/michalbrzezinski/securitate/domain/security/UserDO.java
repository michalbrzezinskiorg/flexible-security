package org.michalbrzezinski.securitate.domain.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Set;

@Value
@Builder
@AllArgsConstructor
public class UserDO {
    Integer id;
    @Size(min = 3, max = 30)
    String name;
    @Size(min = 3, max = 30)
    String surname;
    @Email
    String login;
    boolean active;
    @Singular
    Set<PermissionDO> permissions;
    @Singular
    Set<RoleDO> roles;
}
