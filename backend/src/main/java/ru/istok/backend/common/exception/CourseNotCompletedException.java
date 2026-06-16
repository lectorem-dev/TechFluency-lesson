package ru.istok.backend.common.exception;

import org.springframework.http.HttpStatus;

public class CourseNotCompletedException extends AppException {

    public CourseNotCompletedException() {
        super(HttpStatus.FORBIDDEN, ErrorCode.COURSE_NOT_COMPLETED, "Course is not completed");
    }
}
