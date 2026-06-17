package ru.istok.backend.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Результат проверки теста")
public class LessonSubmitResponse {

    @Schema(description = "Признак успешного прохождения теста", example = "true")
    private Boolean passed;

    @Schema(description = "Результат теста в процентах", example = "100")
    private Integer scorePercent;

    @Schema(description = "Минимальный процент для прохождения", example = "70")
    private Integer passPercent;

    @Schema(description = "Идентификатор следующего урока, если тест пройден", example = "3", nullable = true)
    private Long nextLessonId;

    @Schema(description = "Признак завершения всего курса", example = "false")
    private Boolean courseCompleted;
}
