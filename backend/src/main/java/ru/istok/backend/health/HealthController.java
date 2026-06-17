package ru.istok.backend.health;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
@Tag(name = "Сервис", description = "Служебная проверка доступности сервера")
public class HealthController {

    @GetMapping
    @Operation(summary = "Проверка состояния сервиса")
    @ApiResponse(responseCode = "200", description = "Сервис доступен")
    public Map<String, String> health() {
        return Map.of("status", "OK");
    }
}
