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

--changeset user:3
CREATE TABLE Chat
(
    id uuid PRIMARY KEY
);
--rollback DROP TABLE Chat;

--changeset user:4
CREATE TABLE Message
(
    id        uuid PRIMARY KEY,
    chat      uuid      NOT NULL REFERENCES Chat (id) ON DELETE CASCADE ON UPDATE CASCADE,
    author    uuid      NOT NULL REFERENCES Person (id) ON DELETE CASCADE ON UPDATE CASCADE,
    timestamp timestamp NOT NULL,
    reply     uuid REFERENCES Message (id) ON DELETE CASCADE ON UPDATE CASCADE
);
--rollback DROP TABLE Message;

--changeset user:5
CREATE TABLE Reaction
(
    message uuid REFERENCES Message (id) ON DELETE CASCADE,
    person  uuid REFERENCES Person (id) ON DELETE CASCADE,
    emoji   varchar(50) NOT NULL,
    PRIMARY KEY (message, person)
);
--rollback DROP TABLE Reaction;

--changeset user:6
CREATE TABLE PersonalChat
(
    id      uuid PRIMARY KEY REFERENCES Chat (id) ON DELETE CASCADE ON UPDATE CASCADE,
    person1 uuid NOT NULL REFERENCES Person (id) ON DELETE CASCADE ON UPDATE CASCADE,
    person2 uuid NOT NULL REFERENCES Person (id) ON DELETE CASCADE ON UPDATE CASCADE
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
    person uuid REFERENCES Person (id),
    role   uuid NOT NULL REFERENCES GroupRole (id),
    PRIMARY KEY (pgroup, person)
);
--rollback DROP TABLE GroupMember;


