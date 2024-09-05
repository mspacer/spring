--liquibase formatted sql

--changeset msp:1
ALTER TABLE users
ADD COLUMN image VARCHAR(64);

ALTER TABLE users_aud
ADD COLUMN image VARCHAR(64);
