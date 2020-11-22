--liquibase formatted sql
--changeset stevedonie:create-multiple-tables splitStatements:true endDelimiter:;

create table if not exists `controllers`
(
    id int not null
        primary key AUTO_INCREMENT,
    active bit not null,
    controller varchar(255) null,
    http varchar(255) null,
    method varchar(255) null,
    constraint unique_controller_method_http_for_controllers
        unique (controller, method, http)
);

create table if not exists `roles`
(
    id int not null
        primary key AUTO_INCREMENT,
    active bit null,
    name varchar(255) null,
    constraint unique_name_for_roles
        unique (name)
);

create table if not exists `role_controller`
(
    role_id int not null AUTO_INCREMENT,
    controller_id int not null,
    primary key (role_id, controller_id),
    constraint foreignkey_role_id__in_role_controller
        foreign key (role_id) references roles (id),
    constraint foreignkey_controller_id_in_role_controller
        foreign key (controller_id) references controllers (id)
);

create table if not exists `users`
(
    id int not null
        primary key AUTO_INCREMENT,
    active bit not null,
    login varchar(255) null,
    name varchar(255) null,
    surname varchar(255) null,
    constraint unique_login_for_user
        unique (login)
);

create table if not exists `permissions`
(
    id bigint not null
        primary key AUTO_INCREMENT,
    active bit not null,
    from_date datetime(6) null,
    to_date datetime(6) null,
    created_by int null,
    permission_for int null,
    constraint foreignkey_permission_for_in_permissions
        foreign key (permission_for) references users (id),
    constraint foreignkey_created_by_in_permissions
        foreign key (created_by) references users (id)
);

create table if not exists `controllers_permissions`
(
    controllers_id int not null,
    permissions_id bigint not null,
    primary key (controllers_id, permissions_id),
    constraint foreignkey_permissions_id_in_controllers_permissions
        foreign key (permissions_id) references permissions (id),
    constraint foreignkey_controllers_id_in_controllers_permissions
        foreign key (controllers_id) references controllers (id)
);

create table if not exists `users_roles`
(
    users_id int not null,
    roles_id int not null,
    primary key (users_id, roles_id),
    constraint foreignkey_roles_id_in_users_roles
        foreign key (roles_id) references roles (id),
    constraint foreignkey_users_id_in_users_roles
        foreign key (users_id) references users (id)
);

