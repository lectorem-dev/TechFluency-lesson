-- Форматированный SQL для Liquibase.

-- Расширение pgcrypto используется для генерации UUID через gen_random_uuid().
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Таблица пользователей платформы.
CREATE TABLE users
(
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name          VARCHAR(255) NOT NULL,
    login         VARCHAR(100) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role          VARCHAR(30)  NOT NULL,
    status        VARCHAR(30)  NOT NULL,
    created_at    TIMESTAMP    NOT NULL,
    updated_at    TIMESTAMP    NOT NULL
);

-- Логин пользователя должен быть уникальным.
CREATE UNIQUE INDEX ux_users_login ON users (login);

-- Стартовый администратор.
-- Пароль: secret
-- Значение password_hash хранится в BCrypt-хеше.
INSERT INTO users (name, login, password_hash, role, status, created_at, updated_at)
VALUES ('Администратор',
        'admin',
        '$2a$10$kFPyjFBAOs9HlmskbGrz6uknEnfnXa8quq4WPzHn/lP.qi7t3OAtq',
        'ADMIN',
        'ACTIVE',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP);

-- Таблица курса.
-- В MVP используется один курс, но таблица оставлена универсальной.
CREATE TABLE courses
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title       VARCHAR(255) NOT NULL,
    description TEXT         NULL,
    created_at  TIMESTAMP    NOT NULL,
    updated_at  TIMESTAMP    NOT NULL
);

-- Таблица уроков курса.
-- position задает порядок прохождения уроков.
CREATE TABLE lessons
(
    id               UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    course_id        UUID         NOT NULL REFERENCES courses (id) ON DELETE CASCADE,
    position         INTEGER      NOT NULL,
    title            VARCHAR(255) NOT NULL,
    markdown_content TEXT         NOT NULL,
    created_at       TIMESTAMP    NOT NULL,
    updated_at       TIMESTAMP    NOT NULL,
    CONSTRAINT uq_lessons_course_position UNIQUE (course_id, position)
);

-- Таблица вопросов теста.
-- Каждый вопрос относится к конкретному уроку.
CREATE TABLE test_questions
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    lesson_id  UUID      NOT NULL REFERENCES lessons (id) ON DELETE CASCADE,
    position   INTEGER   NOT NULL,
    text       TEXT      NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- Таблица вариантов ответа.
-- correct хранится только на backend и не должен отдаваться студенту в API урока.
CREATE TABLE test_answers
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    question_id UUID      NOT NULL REFERENCES test_questions (id) ON DELETE CASCADE,
    position    INTEGER   NOT NULL,
    text        TEXT      NOT NULL,
    correct     BOOLEAN   NOT NULL,
    created_at  TIMESTAMP NOT NULL,
    updated_at  TIMESTAMP NOT NULL
);

-- Таблица правил прохождения урока.
-- pass_percent задает минимальный процент правильных ответов для зачета урока.
CREATE TABLE lesson_pass_rules
(
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    lesson_id    UUID      NOT NULL UNIQUE REFERENCES lessons (id) ON DELETE CASCADE,
    pass_percent INTEGER   NOT NULL,
    created_at   TIMESTAMP NOT NULL,
    updated_at   TIMESTAMP NOT NULL,
    CONSTRAINT chk_lesson_pass_rules_pass_percent CHECK (pass_percent BETWEEN 1 AND 100)
);

-- Таблица прогресса пользователя по урокам.
-- Хранит лучший успешный результат по каждому уроку.
CREATE TABLE lesson_progress
(
    id                 UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id            UUID      NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    lesson_id          UUID      NOT NULL REFERENCES lessons (id) ON DELETE CASCADE,
    best_score_percent INTEGER   NOT NULL,
    passed             BOOLEAN   NOT NULL,
    completed_at       TIMESTAMP NULL,
    created_at         TIMESTAMP NOT NULL,
    updated_at         TIMESTAMP NOT NULL,
    CONSTRAINT uq_lesson_progress_user_lesson UNIQUE (user_id, lesson_id),
    CONSTRAINT chk_lesson_progress_best_score CHECK (best_score_percent BETWEEN 0 AND 100)
);