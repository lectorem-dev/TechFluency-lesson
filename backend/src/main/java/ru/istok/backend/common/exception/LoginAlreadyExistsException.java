package ru.istok.backend.common.exception;

import org.springframework.http.HttpStatus;

public class LoginAlreadyExistsException extends AppException {

    public LoginAlreadyExistsException(String login) {
        super(HttpStatus.CONFLICT, ErrorCode.LOGIN_ALREADY_EXISTS, "Login '%s' is already used".formatted(login));
    }
}
