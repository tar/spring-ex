SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET client_min_messages = warning;

CREATE SEQUENCE user_seq
    START WITH 100
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
    
CREATE TABLE users (
    id integer DEFAULT nextval('user_seq'::regclass) NOT NULL,
    login character varying(255) NOT NULL,
    email character varying(255) NOT NULL
);
    
ALTER TABLE ONLY users
    ADD CONSTRAINT pkey_article PRIMARY KEY (id);
