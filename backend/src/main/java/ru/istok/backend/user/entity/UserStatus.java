package ru.istok.backend.user.entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Статус пользователя: ACTIVE — активен, ARCHIVED — в архиве")
public enum UserStatus {
    ACTIVE,
    ARCHIVED
}
