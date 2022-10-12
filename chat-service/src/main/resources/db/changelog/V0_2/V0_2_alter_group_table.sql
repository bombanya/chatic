--liquibase formatted sql

--changeset stolexiy:1
ALTER TABLE PGroup
    ADD COLUMN name text NOT NULL;
--rollback ALTER TABLE PGroup DROP COLUMN name;
