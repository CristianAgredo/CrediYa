package co.com.pragma.r2dbc.usuario;

import co.com.pragma.r2dbc.entity.UsuarioEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UsuarioReactiveRepository extends ReactiveCrudRepository<UsuarioEntity, Long> {
    Mono<Boolean> existsByEmail(String email);
}
