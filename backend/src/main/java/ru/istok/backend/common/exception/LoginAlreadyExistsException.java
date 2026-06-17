package ru.istok.backend.common.exception;

import org.springframework.http.HttpStatus;

public class LoginAlreadyExistsException extends AppException {

    public LoginAlreadyExistsException() {
        super(HttpStatus.CONFLICT, ErrorCode.LOGIN_ALREADY_EXISTS, "Логин уже занят");
    }
}
