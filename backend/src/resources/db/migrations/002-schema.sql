--liquibase formatted sql
--changeset stevedonie:create-multiple-tables splitStatements:true endDelimiter:;

create table if not exists article
(
    url       varchar(255) not null,
    body      varchar(255),
    subject   varchar(255),
    author_id integer,
    primary key (url)
);

create table if not exists author
(
    id      integer not null,
    name    varchar(255),
    surname varchar(255),
    primary key (id)
);


alter table article
    add constraint unuque_articleid_authorid
        foreign key (author_id)
            references author (id);

