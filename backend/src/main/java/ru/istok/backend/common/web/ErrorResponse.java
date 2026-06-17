package ru.istok.backend.common.web;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import ru.istok.backend.common.exception.ErrorCode;

@Schema(description = "Стандартный формат ошибки API")
public record ErrorResponse(
        @Schema(description = "Время формирования ошибки")
        LocalDateTime timestamp,
        @Schema(description = "HTTP-статус ответа", example = "400")
        int status,
        @Schema(description = "Код ошибки приложения", example = "VALIDATION_ERROR")
        ErrorCode code,
        @Schema(description = "Основное сообщение об ошибке", example = "Некорректное тело запроса")
        String message,
        @Schema(description = "Дополнительные детали ошибки")
        List<String> details
) {
    public static ErrorResponse of(int status, ErrorCode code, String message) {
        return new ErrorResponse(LocalDateTime.now(), status, code, message, List.of());
    }

    public static ErrorResponse of(int status, ErrorCode code, String message, List<String> details) {
        return new ErrorResponse(LocalDateTime.now(), status, code, message, details);
    }
}
