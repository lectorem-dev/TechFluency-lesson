package ru.istok.backend.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.istok.backend.user.entity.UserRole;
import ru.istok.backend.user.entity.UserStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Данные для обновления пользователя")
public class UserUpdateRequest {

    @Schema(description = "Имя пользователя", example = "Иван Иванов")
    @NotBlank(message = "Имя обязательно")
    private String name;

    @Schema(description = "Логин пользователя", example = "student")
    @NotBlank(message = "Логин обязателен")
    private String login;

    @Schema(description = "Новый пароль пользователя. Можно не передавать, чтобы оставить старый", nullable = true)
    private String password;

    @Schema(description = "Роль пользователя: ADMIN или STUDENT", example = "STUDENT")
    @NotNull(message = "Роль обязательна")
    private UserRole role;

    @Schema(description = "Статус пользователя: ACTIVE или ARCHIVED", example = "ACTIVE")
    @NotNull(message = "Статус обязателен")
    private UserStatus status;
}
