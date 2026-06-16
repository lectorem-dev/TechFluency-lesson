--liquibase formatted sql

--changeset istok:001-create-users
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    login VARCHAR(100) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(30) NOT NULL,
    status VARCHAR(30) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

--changeset istok:002-create-users-login-index
CREATE UNIQUE INDEX ux_users_login ON users (login);

--changeset istok:003-create-admin
INSERT INTO users (name, login, password_hash, role, status, created_at, updated_at)
VALUES (
    'Администратор',
    'admin',
    '$2a$10$kFPyjFBAOs9HlmskbGrz6uknEnfnXa8quq4WPzHn/lP.qi7t3OAtq',
    'ADMIN',
    'ACTIVE',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);
