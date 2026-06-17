package ru.istok.backend.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ на один вопрос теста")
public class LessonSubmitAnswerRequest {

    @Schema(description = "Идентификатор вопроса", example = "10")
    @NotNull(message = "Идентификатор вопроса обязателен")
    private Long questionId;

    @Schema(description = "Идентификатор выбранного ответа", example = "42")
    @NotNull(message = "Идентификатор ответа обязателен")
    private Long answerId;
}
