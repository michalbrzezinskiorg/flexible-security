package org.michalbrzezinski.securitate.database.security;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
interface RoleRepository extends JpaRepository<Role, Integer> {
    Page<Role> findAll(Pageable pageable);
    List<Role> findAll();

    Optional<Role> findByName(String roleName);
}
