--liquibase formatted sql

--changeset bombanya:10
ALTER TABLE Person
    ADD COLUMN password text NOT NULL;
--rollback ALTER TABLE person DROP COLUMN password;

--changeset bombanya:11
ALTER TABLE Person
    ADD COLUMN auth_role text NOT NULL;
--rollback ALTER TABLE person DROP COLUMN auth_role;