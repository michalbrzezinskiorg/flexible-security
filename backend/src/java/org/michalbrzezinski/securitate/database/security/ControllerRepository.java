package org.michalbrzezinski.securitate.database.security;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Repository
@Transactional
interface ControllerRepository extends PagingAndSortingRepository<ControllerEntity, Integer> {

    Set<ControllerEntity> findAll();

    Page<ControllerEntity> findAll(Pageable pageable);

    @Query("select distinct c from ControllerEntity c left join fetch c.permissions p left join fetch c.roles r left join fetch r.users u1 left join fetch p.permissionFor u2 where u1.id = :user or u2.id = :user")
    List<ControllerEntity> findByUser(Integer user);

    List<ControllerEntity> findByIdIn(List<Integer> listOfIds);

}
