--liquibase formatted sql

--changeset bombanya:10
ALTER TABLE Person ADD COLUMN password text NOT NULL;
--rollback ALTER TABLE person DROP COLUMN password;

--changeset bombanya:11
CREATE TABLE AuthRole (
    id uuid PRIMARY KEY,
    name text NOT NULL UNIQUE
);
--rollback DROP TABLE role;

--changeset bombanya:12
CREATE TABLE PersonRoles (
    person_id uuid NOT NULL REFERENCES Person ON UPDATE CASCADE ON DELETE CASCADE,
    role_id uuid NOT NULL REFERENCES AuthRole ON UPDATE CASCADE ON DELETE RESTRICT,
    PRIMARY KEY (person_id, role_id)
);
--rollback DROP TABLE person_roles;