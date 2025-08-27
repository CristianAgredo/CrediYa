package co.com.pragma.r2dbc;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UsuarioReactiveRepository extends ReactiveCrudRepository<UsuarioData, Long> {
    Mono<Boolean> existsByEmail(String email);
}
