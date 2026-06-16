package ru.istok.backend.common.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends AppException {

    public UserNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, ErrorCode.USER_NOT_FOUND, "User with id %d not found".formatted(id));
    }
}
