package ru.istok.backend.course.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Краткая информация об уроке")
public class LessonListItemResponse {

    @Schema(description = "Идентификатор урока", example = "2")
    private Long id;

    @Schema(description = "Порядковый номер урока", example = "2")
    private Integer position;

    @Schema(description = "Название урока", example = "Урок 2")
    private String title;

    @Schema(description = "Статус урока", example = "AVAILABLE")
    private LessonStatus status;

    @Schema(description = "Признак блокировки урока", example = "false")
    private Boolean locked;

    @Schema(description = "Лучший результат по тесту в процентах", example = "100", nullable = true)
    private Integer bestScorePercent;
}
