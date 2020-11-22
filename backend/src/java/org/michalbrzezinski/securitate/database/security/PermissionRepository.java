package org.michalbrzezinski.securitate.database.security;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository()
@Transactional
interface PermissionRepository extends PagingAndSortingRepository<Permission, Long> {
    Set<Permission> findAll();

    Page<Permission> findAll(Pageable pageable);

    Permission save(Permission permission);
}
