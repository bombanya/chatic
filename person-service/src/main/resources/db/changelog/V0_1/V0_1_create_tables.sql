--liquibase formatted sql

--changeset user:1
CREATE TABLE Person
(
    id       uuid PRIMARY KEY,
    username varchar(20) NOT NULL UNIQUE,
    bio      varchar(70)
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
