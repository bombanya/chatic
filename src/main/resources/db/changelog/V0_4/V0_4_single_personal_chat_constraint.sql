--liquibase formatted sql

--changeset bombanya:12
ALTER TABLE PersonalChat
    ADD CONSTRAINT single_chat UNIQUE(person1, person2);
--rollback ALTER TABLE PersonalChat DROP CONSTRAINT single_chat;