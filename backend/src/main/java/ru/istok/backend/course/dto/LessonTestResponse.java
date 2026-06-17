package ru.istok.backend.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Тест, прикрепленный к уроку")
public class LessonTestResponse {

    @Schema(description = "Минимальный процент для прохождения теста", example = "70")
    private Integer passPercent;

    @Schema(description = "Вопросы теста")
    private List<TestQuestionResponse> questions;
}
