package ru.istok.backend.common.exception;

import org.springframework.http.HttpStatus;

public class LessonNotFoundException extends AppException {

    public LessonNotFoundException() {
        super(HttpStatus.NOT_FOUND, ErrorCode.LESSON_NOT_FOUND, "Урок не найден");
    }
}
