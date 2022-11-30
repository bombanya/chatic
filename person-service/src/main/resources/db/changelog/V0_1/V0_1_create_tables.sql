--liquibase formatted sql

--changeset user:1
CREATE TABLE Person
(
    id       uuid PRIMARY KEY,
    username varchar(20) NOT NULL UNIQUE,
    password text NOT NULL,
    auth_role text NOT NULL,
    bio      varchar(70),
    deleted  bool NOT NULL
);
--rollback DROP TABLE Person;

--changeset user:2
CREATE TABLE Device
(
    id     uuid PRIMARY KEY,
    person uuid        NOT NULL REFERENCES Person (id) ON DELETE CASCADE ON UPDATE CASCADE,
    geo    varchar(50) NOT NULL,
    mac    varchar(17) NOT NULL
);
--rollback DROP TABLE Device;

--changeset user:3
INSERT INTO Person (id, username, password, auth_role, bio, deleted)
VALUES ('c9c8c1fc-7ec0-48e6-be44-46c62d3b5d8a',
        'admin',
        '$2a$12$ZZRbkAJnAHYfjDa.CBy7.OjXbIJUESVjqbn0iIAxysDFspu4jrB7u',
        'ADMIN',
        null,
        false
);
--rollback delete from person where username = 'admin';
