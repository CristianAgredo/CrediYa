package co.com.pragma.api.config;

import co.com.pragma.usecase.usuario.RegistrarUsuarioUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
@Order(-2)
@Slf4j
public class GlobalErrorHandler implements WebExceptionHandler {
    private final ObjectMapper om = new ObjectMapper();

    @Override
    public Mono<Void> handle(ServerWebExchange ex, Throwable t) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String code = "error_generico";
        String message = "Ocurrió un error inesperado.";
        if (t instanceof RegistrarUsuarioUseCase.ReglaNegocio) {
            status = HttpStatus.BAD_REQUEST;
            code = t.getMessage();
            message = "No fue posible procesar la solicitud.";
        }
        log.warn("{} {} -> {}: {}", ex.getRequest().getMethod(), ex.getRequest().getURI(), status, t.toString());
        ex.getResponse().setStatusCode(status);
        ex.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        byte[] bytes;
        try {
            bytes = om.writeValueAsBytes(Map.of("error", code, "message", message));
        } catch (Exception e) {
            bytes = ("{\"error\":\"" + code + "\"}").getBytes(StandardCharsets.UTF_8);
        }
        return ex.getResponse().writeWith(Mono.just(ex.getResponse().bufferFactory().wrap(bytes)));
    }
}

