package ru.istok.backend.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.istok.backend.user.entity.UserRole;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ после успешной авторизации")
public class LoginResponse {

    @Schema(description = "JWT-токен для заголовка Authorization", example = "eyJhbGciOiJIUzI1NiJ9...")
    private String token;

    @Schema(description = "Идентификатор пользователя", example = "2")
    private Long userId;

    @Schema(description = "Логин пользователя", example = "student")
    private String login;

    @Schema(description = "Имя пользователя", example = "Иван Иванов")
    private String name;

    @Schema(description = "Роль пользователя: ADMIN или STUDENT", example = "STUDENT")
    private UserRole role;
}
