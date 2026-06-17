package ru.istok.backend.common.exception;

import org.springframework.http.HttpStatus;

public class ArchivedUserException extends AppException {

    public ArchivedUserException() {
        super(HttpStatus.FORBIDDEN, ErrorCode.USER_ARCHIVED, "Пользователь архивирован");
    }
}
