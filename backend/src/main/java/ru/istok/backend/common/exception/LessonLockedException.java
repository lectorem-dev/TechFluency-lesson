package ru.istok.backend.common.exception;

import org.springframework.http.HttpStatus;

public class LessonLockedException extends AppException {

    public LessonLockedException() {
        super(HttpStatus.FORBIDDEN, ErrorCode.LESSON_LOCKED, "Урок пока недоступен");
    }
}
