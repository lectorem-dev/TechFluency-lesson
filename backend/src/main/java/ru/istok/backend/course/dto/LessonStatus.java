package ru.istok.backend.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Статус урока: LOCKED — закрыт, AVAILABLE — доступен, PASSED — пройден")
public enum LessonStatus {
    LOCKED,
    AVAILABLE,
    PASSED
}
