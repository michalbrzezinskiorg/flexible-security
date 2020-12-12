package org.michalbrzezinski.securitate.database.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
interface UserRepository extends JpaRepository<UserEntity, Integer> {
    List<UserEntity> findAll();

    Optional<UserEntity> findByLogin(String login);
}
