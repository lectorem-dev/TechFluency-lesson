package ru.istok.backend.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.istok.backend.auth.dto.LoginRequest;
import ru.istok.backend.auth.dto.LoginResponse;
import ru.istok.backend.auth.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Авторизация", description = "Вход пользователя в систему")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(
            summary = "Авторизация пользователя",
            description = "Проверяет логин и пароль и возвращает JWT-токен для дальнейших запросов"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "JWT-токен и данные пользователя"),
            @ApiResponse(responseCode = "401", description = "Неверный логин или пароль"),
            @ApiResponse(responseCode = "403", description = "Пользователь архивирован")
    })
    public LoginResponse login(
            @Valid
            @RequestBody(description = "Логин и пароль пользователя", required = true)
            @org.springframework.web.bind.annotation.RequestBody LoginRequest request
    ) {
        return authService.login(request);
    }
}
