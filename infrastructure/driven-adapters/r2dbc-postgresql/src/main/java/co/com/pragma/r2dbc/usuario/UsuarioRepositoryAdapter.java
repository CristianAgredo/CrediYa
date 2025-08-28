package co.com.pragma.r2dbc.usuario;

import co.com.pragma.model.usuario.Usuario;
import co.com.pragma.model.usuario.gateways.UsuarioRepositoryGateway;
import co.com.pragma.r2dbc.entity.UsuarioEntity;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UsuarioRepositoryAdapter implements UsuarioRepositoryGateway {

    private final UsuarioReactiveRepository repository;
    private final ObjectMapper mapper;

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public Mono<Usuario> save(Usuario usuario) {
        UsuarioEntity data = mapper.map(usuario, UsuarioEntity.class);
        return repository.save(data).map(saved -> mapper.map(saved, Usuario.class));
    }
}