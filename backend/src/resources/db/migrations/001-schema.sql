--liquibase formatted sql
--changeset stevedonie:create-multiple-tables splitStatements:true endDelimiter:;


create table author
(
    id      int          not null primary key auto_increment,
    name    varchar(255) null,
    surname varchar(255) null
);

create table article
(
    url       varchar(255) not null primary key,
    body      varchar(255) null,
    subject   varchar(255) null,
    author_id int          null,
    constraint article_author_id
        foreign key (author_id) references author (id)
);

create table controllers
(
    id         int primary key auto_increment,
    active     bit          not null,
    controller varchar(255) null,
    http       varchar(255) null,
    method     varchar(255) null,
    constraint controllers_controller_method_http
        unique (controller, method, http)
);

create table roles
(
    id     int primary key auto_increment,
    active bit          null,
    name   varchar(255) null,
    constraint roles_name
        unique (name)
);

create table role_controller
(
    role_id       int not null,
    controller_id int not null,
    primary key (role_id, controller_id),
    constraint role_controller_role_id
        foreign key (role_id) references roles (id),
    constraint role_controller_controller_id
        foreign key (controller_id) references controllers (id)
);

create table users
(
    id      int primary key auto_increment,
    active  bit          not null,
    login   varchar(255) null,
    name    varchar(255) null,
    surname varchar(255) null,
    constraint users_login
        unique (login)
);

create table permissions
(
    id             bigint primary key auto_increment,
    active         bit          not null,
    created_by     varchar(255) null,
    from_date      datetime(6)  null,
    to_date        datetime(6)  null,
    permission_for int          null,
    constraint permissions_permission_for
        foreign key (permission_for) references users (id)
);

create table permissions_controllers
(
    permission_entity_id bigint not null,
    controllers_id       int    not null,
    primary key (permission_entity_id, controllers_id),
    constraint permissions_controllers_permission_entity_id
        foreign key (permission_entity_id) references permissions (id),
    constraint permissions_controllers_controllers_id
        foreign key (controllers_id) references controllers (id)
);

create table users_roles
(
    user_entity_id int not null,
    roles_id       int not null,
    primary key (user_entity_id, roles_id),
    constraint users_roles_user_entity_id_roles_id
        foreign key (user_entity_id) references users (id),
    constraint users_roles_roles_id
        foreign key (roles_id) references roles (id)
);

