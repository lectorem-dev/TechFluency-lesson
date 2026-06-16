package ru.istok.backend.user.mapper;

import org.springframework.stereotype.Component;
import ru.istok.backend.user.dto.UserResponse;
import ru.istok.backend.user.entity.User;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getLogin(),
                user.getRole(),
                user.getStatus(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
