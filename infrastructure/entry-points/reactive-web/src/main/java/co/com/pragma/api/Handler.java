package co.com.pragma.api;

import co.com.pragma.model.usuario.Usuario;
import co.com.pragma.usecase.usuario.RegistrarUsuarioUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class Handler {

    private final RegistrarUsuarioUseCase useCase;

    public Mono<ServerResponse> crearUsuario(ServerRequest req) {
        return req.bodyToMono(Usuario.class)
                .doOnNext(u -> log.info("Solicitud crear usuario email={}", u.getEmail()))
                .flatMap(useCase::ejecutar)
                .doOnSuccess(u -> log.info("Usuario creado id={}", u.getId()))
                .doOnError(e -> log.warn("Fallo creando usuario: {}", e.getMessage()))
                .flatMap(u -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(u));
    }
}
