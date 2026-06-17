package ru.istok.backend.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Вариант ответа на вопрос")
public class TestAnswerResponse {

    @Schema(description = "Идентификатор ответа", example = "42")
    private Long id;

    @Schema(description = "Текст ответа", example = "Markdown используется для описания учебного материала")
    private String text;
}
