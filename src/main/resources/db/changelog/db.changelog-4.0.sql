--liquibase formatted sql

--changeset msp:1
ALTER TABLE users_aud
DROP CONSTRAINT users_aud_username_key;
