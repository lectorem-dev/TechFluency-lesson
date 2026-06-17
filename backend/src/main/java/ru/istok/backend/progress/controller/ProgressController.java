package ru.istok.backend.progress.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.istok.backend.progress.dto.CourseProgressResponse;
import ru.istok.backend.progress.service.ProgressService;

@RestController
@RequestMapping("/api/course/progress")
@RequiredArgsConstructor
@Tag(name = "Прогресс курса", description = "Текущий прогресс пользователя по курсу")
public class ProgressController {

    private final ProgressService progressService;

    @GetMapping
    @Operation(summary = "Получение текущего прогресса пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Прогресс по курсу"),
            @ApiResponse(responseCode = "404", description = "Курс не найден")
    })
    public CourseProgressResponse getCourseProgress() {
        return progressService.getCourseProgress();
    }
}
