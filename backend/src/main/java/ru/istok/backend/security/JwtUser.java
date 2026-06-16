package ru.istok.backend.security;

import ru.istok.backend.user.entity.UserRole;

public record JwtUser(Long userId, String login, UserRole role) {
}
