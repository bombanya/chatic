--liquibase formatted sql

--changeset stolexiy:1
CREATE TABLE MessageContent
(
    id         uuid PRIMARY KEY,
    message_id uuid NOT NULL REFERENCES Message ON UPDATE CASCADE ON DELETE CASCADE,
    text       text NOT NULL
);
--rollback DROP TABLE messagecontent;
