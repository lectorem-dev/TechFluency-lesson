package ru.istok.backend.user.entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Роль пользователя: ADMIN — администратор, STUDENT — студент")
public enum UserRole {
    ADMIN,
    STUDENT
}
