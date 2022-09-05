--liquibase formatted sql

--changeset user:1
CREATE TABLE Person (
    id uuid PRIMARY KEY
);
--rollback DROP TABLE Person;

--changeset user:2
CREATE TABLE Device (
    id uuid PRIMARY KEY,
    person uuid REFERENCES Person (id) ON DELETE CASCADE,
    geo varchar(50),
    mac varchar(50)
);
--rollback DROP TABLE Device;

--changeset user:3
CREATE TABLE Chat (
    id uuid PRIMARY KEY
);
--rollback DROP TABLE Chat ;

--changeset user:4
CREATE TABLE Message (
        id uuid PRIMARY KEY,
        chat uuid REFERENCES Chat (id) ON DELETE CASCADE,
        author uuid REFERENCES Person (id) ON DELETE CASCADE,
        timestamp date NOT NULL,
        reply uuid REFERENCES Message (id) ON DELETE CASCADE
);
--rollback DROP TABLE Message;

--changeset user:5
CREATE TABLE Reaction (
        message uuid REFERENCES Message (id) ON DELETE CASCADE,
        person uuid REFERENCES Person (id) ON DELETE CASCADE,
        emoji varchar(50),
        PRIMARY KEY (message, person)
);
--rollback DROP TABLE Reaction;

--changeset user:6
CREATE TABLE PersonalChat (
        chat uuid REFERENCES Chat (id) ON DELETE CASCADE,
        person1 uuid REFERENCES Person (id) ON DELETE CASCADE,
        person2 uuid REFERENCES Person (id) ON DELETE CASCADE,
        PRIMARY KEY (chat, person1, person2)
);
--rollback DROP TABLE PersonalChat;

--changeset user:7
CREATE TABLE PGroup (
        chat uuid REFERENCES Chat (id),
        PRIMARY KEY (chat)
);
--rollback DROP TABLE PGroup;

--changeset user:8
CREATE TABLE GroupRole (
        id uuid PRIMARY KEY,
        write_posts varchar(50),
        write_comments varchar(50),
        banhammer varchar(50)
);
--rollback DROP TABLE GroupRole;

--changeset user:9
CREATE TABLE GroupMember (
        pgroup uuid REFERENCES PGroup (chat),
        person uuid REFERENCES Person (id),
        role uuid REFERENCES GroupRole (id),
        PRIMARY KEY (pgroup, person, role)
);
--rollback DROP TABLE GroupMember;




