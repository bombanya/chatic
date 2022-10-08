--liquibase formatted sql

--changeset user:3
CREATE TABLE Chat
(
    id uuid PRIMARY KEY
);
--rollback DROP TABLE Chat;

--changeset user:6
CREATE TABLE PersonalChat
(
    id      uuid PRIMARY KEY REFERENCES Chat (id) ON DELETE CASCADE ON UPDATE CASCADE,
    person1 uuid NOT NULL,
    person2 uuid NOT NULL
);
--rollback DROP TABLE PersonalChat;

--changeset user:7
CREATE TABLE PGroup
(
    id uuid PRIMARY KEY REFERENCES Chat (id) ON DELETE CASCADE ON UPDATE CASCADE
);
--rollback DROP TABLE PGroup;

--changeset user:8
CREATE TABLE GroupRole
(
    id             uuid PRIMARY KEY,
    write_posts    boolean NOT NULL,
    write_comments boolean NOT NULL,
    manage_members boolean NOT NULL
);
--rollback DROP TABLE GroupRole;

--changeset user:9
CREATE TABLE GroupMember
(
    pgroup uuid REFERENCES PGroup (id),
    person uuid,
    role   uuid NOT NULL REFERENCES GroupRole (id),
    PRIMARY KEY (pgroup, person)
);
--rollback DROP TABLE GroupMember;

--changeset bombanya:12
ALTER TABLE PersonalChat
    ADD CONSTRAINT single_chat UNIQUE (person1, person2);
--rollback ALTER TABLE PersonalChat DROP CONSTRAINT single_chat;