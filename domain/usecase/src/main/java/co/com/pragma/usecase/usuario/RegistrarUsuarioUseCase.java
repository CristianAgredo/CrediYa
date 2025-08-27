package co.com.pragma.usecase.usuario;

import co.com.pragma.model.usuario.Usuario;
import co.com.pragma.model.usuario.gateways.UsuarioRepositoryGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RegistrarUsuarioUseCase {

    private final UsuarioRepositoryGateway repo;

    public Mono<Usuario> ejecutar(Usuario u) {
        return repo.existsByEmail(u.getEmail())
                .flatMap(existe -> existe
                        ? Mono.error(new ReglaNegocio("email_duplicado"))
                        : repo.save(u));
    }

    public static class ReglaNegocio extends RuntimeException {
        public ReglaNegocio(String msg) { super(msg); }
    }
}