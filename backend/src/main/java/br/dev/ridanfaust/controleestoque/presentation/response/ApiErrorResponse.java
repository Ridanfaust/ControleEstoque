package br.dev.ridanfaust.controleestoque.presentation.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse {
    private final int status;
    private final String message;
    private final String path;
    private final LocalDateTime timestamp;
    private final Map<String, String> errors;

    public ApiErrorResponse(int status, String message, String path) {
        this(status, message, path, null);
    }

    public ApiErrorResponse(int status, String message, String path, Map<String, String> errors) {
        this.status = status;
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
        this.errors = errors;
    }
}
