package ru.istok.backend.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.istok.backend.user.entity.UserRole;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String token;
    private Long userId;
    private String login;
    private String name;
    private UserRole role;
}
