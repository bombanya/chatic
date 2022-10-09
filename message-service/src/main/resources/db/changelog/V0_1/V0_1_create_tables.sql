--liquibase formatted sql

--changeset user:4
CREATE TABLE Message
(
    id        uuid PRIMARY KEY,
    chat      uuid NOT NULL,
    author    uuid NOT NULL,
    timestamp timestamp NOT NULL,
    reply     uuid REFERENCES Message (id) ON DELETE CASCADE ON UPDATE CASCADE
);
--rollback DROP TABLE Message;

--changeset user:5
CREATE TABLE Reaction
(
    id      uuid PRIMARY KEY,
    message uuid REFERENCES Message (id) ON DELETE CASCADE ON UPDATE CASCADE,
    person  uuid NOT NULL,
    emoji   varchar(50) NOT NULL,
    UNIQUE (message, person)
);
--rollback DROP TABLE Reaction;

--changeset stolexiy:1
CREATE TABLE MessageContent
(
    id         uuid PRIMARY KEY,
    message_id uuid NOT NULL REFERENCES Message ON UPDATE CASCADE ON DELETE CASCADE,
    text       text NOT NULL
);
--rollback DROP TABLE messagecontent;
