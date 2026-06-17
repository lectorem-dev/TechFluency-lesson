package ru.istok.backend.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос на авторизацию")
public class LoginRequest {

    @Schema(description = "Логин пользователя", example = "student")
    @NotBlank(message = "Логин обязателен")
    private String login;

    @Schema(description = "Пароль пользователя", example = "secret")
    @NotBlank(message = "Пароль обязателен")
    private String password;
}
