/*DROP TABLE IF EXISTS person, device, chat, message, reaction, personalchat, pgroup, grouprole, groupmember, messagecontent CASCADE ;

CREATE TABLE Person
(
    id       uuid PRIMARY KEY,
    username varchar(20) NOT NULL UNIQUE,
    bio      varchar(70),
    password text NOT NULL,
    auth_role text NOT NULL
);

CREATE TABLE Device
(
    id     uuid PRIMARY KEY,
    person uuid        NOT NULL REFERENCES Person (id) ON DELETE CASCADE ON UPDATE CASCADE,
    geo    varchar(50) NOT NULL,
    mac    varchar(17) NOT NULL
);

CREATE TABLE Chat
(
    id uuid PRIMARY KEY
);

CREATE TABLE Message
(
    id        uuid PRIMARY KEY,
    chat      uuid      NOT NULL REFERENCES Chat (id) ON DELETE CASCADE ON UPDATE CASCADE,
    author    uuid      NOT NULL REFERENCES Person (id) ON DELETE CASCADE ON UPDATE CASCADE,
    timestamp timestamp NOT NULL,
    reply     uuid REFERENCES Message (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Reaction
(
    message uuid REFERENCES Message (id) ON DELETE CASCADE,
    person  uuid REFERENCES Person (id) ON DELETE CASCADE,
    emoji   varchar(50) NOT NULL,
    PRIMARY KEY (message, person)
);

CREATE TABLE PersonalChat
(
    id      uuid PRIMARY KEY REFERENCES Chat (id) ON DELETE CASCADE ON UPDATE CASCADE,
    person1 uuid NOT NULL REFERENCES Person (id) ON DELETE CASCADE ON UPDATE CASCADE,
    person2 uuid NOT NULL REFERENCES Person (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE PGroup
(
    id uuid PRIMARY KEY REFERENCES Chat (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE GroupRole
(
    id             uuid PRIMARY KEY,
    write_posts    boolean NOT NULL,
    write_comments boolean NOT NULL,
    manage_members boolean NOT NULL
);

CREATE TABLE GroupMember
(
    pgroup uuid REFERENCES PGroup (id),
    person uuid REFERENCES Person (id),
    role   uuid NOT NULL REFERENCES GroupRole (id),
    PRIMARY KEY (pgroup, person)
);

CREATE TABLE MessageContent
(
    id         uuid PRIMARY KEY,
    message_id uuid NOT NULL REFERENCES Message ON UPDATE CASCADE ON DELETE CASCADE,
    text       text NOT NULL
);

ALTER TABLE PersonalChat
    ADD CONSTRAINT single_chat UNIQUE(person1, person2);*/


