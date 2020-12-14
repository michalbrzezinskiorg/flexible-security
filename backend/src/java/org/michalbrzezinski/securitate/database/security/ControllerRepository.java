package org.michalbrzezinski.securitate.database.security;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    List<ControllerEntity> findByIdIn(List<Integer> listOfIds);

}
