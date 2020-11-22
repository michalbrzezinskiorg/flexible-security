package org.michalbrzezinski.securitate.feature.security;

import org.michalbrzezinski.securitate.domain.security.objects.ControllerDO;
import org.michalbrzezinski.securitate.domain.security.objects.PermissionDO;
import org.michalbrzezinski.securitate.domain.security.objects.RoleDO;
import org.michalbrzezinski.securitate.domain.security.objects.UserDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SecurityServiceForManagemet {
    Optional<UserDO> getUser(Integer id);

    Page<RoleDO> getAllRoles(Pageable pageable);

    Page<UserDO> getAllUsers(Pageable pageable);

    Page<PermissionDO> getAllPermissions(Pageable pageable);

    Page<ControllerDO> getAllControllers(Pageable pageable);

    Optional<RoleDO> getRoleById(Integer id);

    List<ControllerDO> getControllersByIds(List<Integer> collect);
}
