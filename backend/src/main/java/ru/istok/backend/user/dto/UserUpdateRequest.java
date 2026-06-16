package ru.istok.backend.user.dto;

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
public class UserUpdateRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String login;

    private String password;

    @NotNull
    private UserRole role;

    @NotNull
    private UserStatus status;
}
