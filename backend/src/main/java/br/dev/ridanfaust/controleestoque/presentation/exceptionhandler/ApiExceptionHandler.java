package br.dev.ridanfaust.controleestoque.presentation.exceptionhandler;

import br.dev.ridanfaust.controleestoque.domain.exception.RecursoNaoEncontradoException;
import br.dev.ridanfaust.controleestoque.domain.exception.RegraNegocioException;
import br.dev.ridanfaust.controleestoque.presentation.response.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ApiExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> validationErrors(MethodArgumentNotValidException ex,
                                                             HttpServletRequest request) {
        logger.error("Erro de validação de campo: {}", ex.getMessage());

        Map<String, String> campos = ex.getBindingResult().getFieldErrors().stream()
                .collect(HashMap::new, (map, error) -> map.put(error.getField(), error.getDefaultMessage()), HashMap::putAll);

        ApiErrorResponse errorResponse = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação",
                request.getRequestURI(),
                campos);

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ApiErrorResponse> regraNegocio(RegraNegocioException ex, HttpServletRequest request) {
        logger.error("Erro de regra de negócio: {}", ex.getMessage());

        Map<String, String> erros = ex.getMensagens().stream()
                .collect(HashMap::new, (map, error) -> map.put("erro", error), HashMap::putAll);

        ApiErrorResponse errorResponse = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                request.getRequestURI(),
                erros);

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ApiErrorResponse> regraNegocio(RecursoNaoEncontradoException ex, HttpServletRequest request) {
        logger.error("Erro de objeto não encontrado: {}", ex.getMessage());

        ApiErrorResponse errorResponse = new ApiErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                request.getRequestURI());

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> globalExceptionHandler(Exception ex, HttpServletRequest request) {
        logger.error("Erro: {}", ex.getMessage());

        ApiErrorResponse errorResponse = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Erro inesperado",
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

}
