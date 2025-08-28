package co.com.pragma.usecase.usuario;

import co.com.pragma.model.usuario.Usuario;
import co.com.pragma.model.usuario.gateways.UsuarioRepositoryGateway;
import co.com.pragma.model.usuario.gateways.UsuarioValidacion;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@RequiredArgsConstructor
public class RegistrarUsuarioUseCase {

    private static final Logger logger = Logger.getLogger(RegistrarUsuarioUseCase.class.getName());


    private final UsuarioRepositoryGateway repo;
    private final UsuarioValidacion validacion;



    public Mono<Usuario> ejecutar(Usuario u) {

        logger.info("registro_usuario.start : " + u.getEmail());
        logger.info("Iniciando el proceso de registro de usuario");
        return Mono.defer(() -> validacion.validate(u))
                .then(repo.existsByEmail(u.getEmail()))
                .flatMap(existe -> existe
                        ? Mono.error(new ReglaNegocio("email_duplicado"))
                        : repo.save(u));
    }

    public static class ReglaNegocio extends RuntimeException {
        public ReglaNegocio(String msg) { super(msg); }
    }
}