package org.michalbrzezinski.securitate.database.security;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
interface ControllerRepository extends PagingAndSortingRepository<Controller, Integer> {

    Set<Controller> findAll();

    Page<Controller> findAll(Pageable pageable);

    @Query("select distinct c from Controller c left join fetch c.permissions p left join fetch c.roles r left join fetch r.users u1 left join fetch p.permissionFor u2 where u1.id = :user or u2.id = :user")
    List<Controller> findByUser(Integer user);

    Collection<Controller> findByIdIn(List<Integer> listOfIds);


    // desired
    //

    // select * from controllers c, controllers_permissions cp, permissions p, users u, role_controller rc, users_roles ur, roles r where
    // ((c.id = cp.controllers_id and cp.permissions_id = p.id and p.permission_for = u.id)
    // OR
    // (c.id = rc.controller_id and rc.role_id = r.id and ur.roles_id and ur.users_id = u.id))
    // and u.id = 4

    // got
    //

    // select
    //        distinct controller0_.id as id1_0_0_,
    //        permission2_.id as id1_2_1_,
    //        role4_.id as id1_4_2_,
    //        user6_.id as id1_5_3_,
    //        user7_.id as id1_5_4_,
    //        controller0_.active as active2_0_0_,
    //        controller0_.controller as controll3_0_0_,
    //        controller0_.http as http4_0_0_,
    //        controller0_.method as method5_0_0_,
    //        permission2_.active as active2_2_1_,
    //        permission2_.created_by as created_5_2_1_,
    //        permission2_.from_date as from_dat3_2_1_,
    //        permission2_.permission_for as permissi6_2_1_,
    //        permission2_.to_date as to_date4_2_1_,
    //        permission1_.controllers_id as controll1_1_0__,
    //        permission1_.permissions_id as permissi2_1_0__,
    //        role4_.active as active2_4_2_,
    //        role4_.name as name3_4_2_,
    //        roles3_.controller_id as controll2_3_1__,
    //        roles3_.role_id as role_id1_3_1__,
    //        user6_.active as active2_5_3_,
    //        user6_.login as login3_5_3_,
    //        user6_.name as name4_5_3_,
    //        user6_.surname as surname5_5_3_,
    //        users5_.roles_id as roles_id2_6_2__,
    //        users5_.users_id as users_id1_6_2__,
    //        user7_.active as active2_5_4_,
    //        user7_.login as login3_5_4_,
    //        user7_.name as name4_5_4_,
    //        user7_.surname as surname5_5_4_
    //    from
    //        controllers controller0_
    //    inner join
    //        controllers_permissions permission1_
    //            on controller0_.id=permission1_.controllers_id
    //    inner join
    //        permissions permission2_
    //            on permission1_.permissions_id=permission2_.id
    //    inner join
    //        role_controller roles3_
    //            on controller0_.id=roles3_.controller_id
    //    inner join
    //        roles role4_
    //            on roles3_.role_id=role4_.id
    //    inner join
    //        users_roles users5_
    //            on role4_.id=users5_.roles_id
    //    inner join
    //        users user6_
    //            on users5_.users_id=user6_.id
    //    inner join
    //        users user7_
    //            on permission2_.permission_for=user7_.id
    //    where
    //        user6_.id=?
    //        or user7_.id=?


}
