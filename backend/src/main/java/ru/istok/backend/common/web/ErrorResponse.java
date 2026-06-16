package ru.istok.backend.common.web;

import java.time.LocalDateTime;
import java.util.List;
import ru.istok.backend.common.exception.ErrorCode;

public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        ErrorCode code,
        String message,
        List<String> details
) {
    public static ErrorResponse of(int status, ErrorCode code, String message) {
        return new ErrorResponse(LocalDateTime.now(), status, code, message, List.of());
    }

    public static ErrorResponse of(int status, ErrorCode code, String message, List<String> details) {
        return new ErrorResponse(LocalDateTime.now(), status, code, message, details);
    }
}
