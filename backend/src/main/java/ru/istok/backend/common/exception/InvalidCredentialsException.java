package ru.istok.backend.common.exception;

import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends AppException {

    public InvalidCredentialsException() {
        super(HttpStatus.UNAUTHORIZED, ErrorCode.INVALID_LOGIN_OR_PASSWORD, "Invalid login or password");
    }
}
