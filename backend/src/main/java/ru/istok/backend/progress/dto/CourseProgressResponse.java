package ru.istok.backend.progress.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Текущий прогресс пользователя по курсу")
public class CourseProgressResponse {

    @Schema(description = "Количество пройденных уроков", example = "2")
    private Integer completedLessons;

    @Schema(description = "Общее количество уроков", example = "3")
    private Integer totalLessons;

    @Schema(description = "Процент прохождения курса", example = "67")
    private Integer progressPercent;

    @Schema(description = "Признак завершения курса", example = "false")
    private Boolean courseCompleted;
}
