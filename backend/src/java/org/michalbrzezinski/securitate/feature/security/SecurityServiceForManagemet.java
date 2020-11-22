package org.michalbrzezinski.securitate.feature.security;

import org.michalbrzezinski.securitate.domain.security.ControllerDO;
import org.michalbrzezinski.securitate.domain.security.PermissionDO;
import org.michalbrzezinski.securitate.domain.security.RoleDO;
import org.michalbrzezinski.securitate.domain.security.UserDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SecurityServiceForManagemet {
    UserDO getUser(Integer id);

    Page<RoleDO> getAllRoles(Pageable pageable);

    Page<UserDO> getAllUsers(Pageable pageable);

    Page<PermissionDO> getAllPermissions(Pageable pageable);

    Page<ControllerDO> getAllControllers(Pageable pageable);

    ControllerDO getControllerById(Integer id);

    RoleDO getRoleById(Integer id);

    List<ControllerDO> getControllersById(List<Integer> collect);
}
