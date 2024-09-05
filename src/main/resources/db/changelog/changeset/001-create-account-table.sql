-- liquibase formatted sql
-- changeset Anastasia Martova:create-account-table

CREATE TABLE accounts (
  id UUID PRIMARY KEY,
  email VARCHAR(255),
  city VARCHAR(50),
  country VARCHAR(50),
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100) NOT NULL,
  phone VARCHAR(255),
  about VARCHAR(255),
  profile_cover VARCHAR(255),
  birth_date TIMESTAMP,
  blocked BOOLEAN NOT NULL,
  deleted BOOLEAN NOT NULL,
  online BOOLEAN NOT NULL,
  created_on TIMESTAMP NOT NULL,
  updated_on TIMESTAMP NOT NULL
);
