package co.com.pragma.r2dbc;

import co.com.pragma.model.usuario.Usuario;
import co.com.pragma.model.usuario.gateways.UsuarioRepositoryGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UsuarioRepositoryAdapter implements UsuarioRepositoryGateway {
    private final UsuarioReactiveRepository repo;

    private static Usuario toDomain(UsuarioData d) {
        return Usuario.builder()
                .id(d.getId())
                .nombres(d.getNombres())
                .apellidos(d.getApellidos())
                .email(d.getEmail())
                .fechaNacimiento(d.getFecha_nacimiento())
                .salarioBase(d.getSalario_base())
                .direccion(d.getDireccion())
                .telefono(d.getTelefono())
                .rol(d.getRol())
                .build();
    }

    private static UsuarioData toData(Usuario u) {
        return UsuarioData.builder()
                .id(u.getId())
                .nombres(u.getNombres())
                .apellidos(u.getApellidos())
                .email(u.getEmail())
                .fecha_nacimiento(u.getFechaNacimiento())
                .salario_base(u.getSalarioBase())
                .direccion(u.getDireccion())
                .telefono(u.getTelefono())
                .rol(u.getRol())
                .build();
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return repo.existsByEmail(email).defaultIfEmpty(false);
    }

    @Override
    public Mono<Usuario> save(Usuario usuario) {
        return repo.save(toData(usuario)).map(UsuarioRepositoryAdapter::toDomain);
    }
}