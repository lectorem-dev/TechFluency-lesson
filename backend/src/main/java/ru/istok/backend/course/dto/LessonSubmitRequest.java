package ru.istok.backend.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Набор ответов студента на тест урока")
public class LessonSubmitRequest {

    @Valid
    @NotEmpty(message = "Нужно передать ответы на вопросы")
    @Schema(description = "Ответы по вопросам урока")
    private List<LessonSubmitAnswerRequest> answers;
}
