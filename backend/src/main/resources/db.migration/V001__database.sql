--liquibase formatted sql logicalFilePath:db/changelog/db.changelog-master.sql
-- Форматированный SQL для Liquibase. Технические директивы `liquibase formatted sql` и `changeset` менять нельзя.

--changeset istok:001-create-users logicalFilePath:db/changelog/db.changelog-master.sql
--validCheckSum: 9:87101dbcace72e1dc92df4b1e39290b1
-- Создание таблицы пользователей.
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

--changeset istok:002-create-users-login-index logicalFilePath:db/changelog/db.changelog-master.sql
--validCheckSum: 9:7fea4942510933577e87b6a75a965da2
-- Создание уникального индекса на логин пользователя.
CREATE UNIQUE INDEX ux_users_login ON users (login);

--changeset istok:003-create-admin logicalFilePath:db/changelog/db.changelog-master.sql
--validCheckSum: 9:3d81239d62cc46ffc5724332a11fcc11
-- Добавление администратора по умолчанию.
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

--changeset istok:004-create-course-content-progress logicalFilePath:db/changelog/db.changelog-master.sql
--validCheckSum: 9:6ecf0f433f4501a051ee379d018be593
-- Создание таблиц курсов, уроков, тестов и прогресса пользователей.
CREATE TABLE courses (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE TABLE lessons (
    id BIGSERIAL PRIMARY KEY,
    course_id BIGINT NOT NULL REFERENCES courses(id),
    position INTEGER NOT NULL,
    title VARCHAR(255) NOT NULL,
    markdown_content TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT uq_lessons_course_position UNIQUE (course_id, position)
);

CREATE TABLE test_questions (
    id BIGSERIAL PRIMARY KEY,
    lesson_id BIGINT NOT NULL REFERENCES lessons(id) ON DELETE CASCADE,
    position INTEGER NOT NULL,
    text TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE TABLE test_answers (
    id BIGSERIAL PRIMARY KEY,
    question_id BIGINT NOT NULL REFERENCES test_questions(id) ON DELETE CASCADE,
    position INTEGER NOT NULL,
    text TEXT NOT NULL,
    correct BOOLEAN NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE TABLE lesson_pass_rules (
    id BIGSERIAL PRIMARY KEY,
    lesson_id BIGINT NOT NULL UNIQUE REFERENCES lessons(id) ON DELETE CASCADE,
    pass_percent INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE TABLE lesson_progress (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    lesson_id BIGINT NOT NULL REFERENCES lessons(id) ON DELETE CASCADE,
    best_score_percent INTEGER NOT NULL,
    passed BOOLEAN NOT NULL,
    completed_at TIMESTAMP NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT uq_lesson_progress_user_lesson UNIQUE (user_id, lesson_id)
);
