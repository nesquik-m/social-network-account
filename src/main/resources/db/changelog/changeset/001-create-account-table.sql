-- liquibase formatted sql
-- changeset Anastasia Martova:create-account-table

CREATE TABLE accounts (
  id UUID PRIMARY KEY,
  email VARCHAR(255),
  city VARCHAR(50),
  country VARCHAR(50),
  first_name VARCHAR(100),
  last_name VARCHAR(100),
  birth_date TIMESTAMP,
  blocked BOOLEAN,
  deleted BOOLEAN,
  online BOOLEAN,
  created_on TIMESTAMP,
  updated_on TIMESTAMP
);
