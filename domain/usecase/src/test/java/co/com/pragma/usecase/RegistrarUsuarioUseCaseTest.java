package co.com.pragma.usecase.usuario;

import co.com.pragma.model.usuario.Usuario;
import co.com.pragma.model.usuario.gateways.UsuarioRepositoryGateway;
import co.com.pragma.model.usuario.gateways.UsuarioValidacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrarUsuarioUseCaseTest {

    @Mock
    private UsuarioRepositoryGateway repo;

    @Mock
    private UsuarioValidacion validacion;

    private RegistrarUsuarioUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new RegistrarUsuarioUseCase(repo, validacion);
    }

    private Usuario usuarioConEmail(String email) {
        // Evita depender de constructores/setters: mockeamos el getter
        Usuario u = mock(Usuario.class);
        when(u.getEmail()).thenReturn(email);
        return u;
    }

    @Test
    void registrarUsuario_exitoso_cuandoNoExisteEmail() {
        // Arrange
        Usuario u = usuarioConEmail("nuevo@correo.com");
        when(validacion.validate(u)).thenReturn(Mono.empty());
        when(repo.existsByEmail("nuevo@correo.com")).thenReturn(Mono.just(false));
        when(repo.save(u)).thenReturn(Mono.just(u));

        // Act & Assert
        StepVerifier.create(useCase.ejecutar(u))
                .expectNext(u)
                .verifyComplete();

        // Verify order: primero valida, luego consulta existencia, luego guarda
        InOrder inOrder = inOrder(validacion, repo);
        inOrder.verify(validacion).validate(u);
        inOrder.verify(repo).existsByEmail("nuevo@correo.com");
        inOrder.verify(repo).save(u);

        verifyNoMoreInteractions(validacion, repo);
    }

    @Test
    void registrarUsuario_falla_cuandoEmailDuplicado() {
        // Arrange
        Usuario u = usuarioConEmail("repetido@correo.com");
        when(validacion.validate(u)).thenReturn(Mono.empty());
        when(repo.existsByEmail("repetido@correo.com")).thenReturn(Mono.just(true));

        // Act & Assert
        StepVerifier.create(useCase.ejecutar(u))
                .expectErrorSatisfies(throwable ->
                        org.assertj.core.api.Assertions.assertThat(throwable)
                                .isInstanceOf(RegistrarUsuarioUseCase.ReglaNegocio.class)
                                .hasMessage("email_duplicado")
                )
                .verify();

        verify(validacion).validate(u);
        verify(repo).existsByEmail("repetido@correo.com");
        verify(repo, never()).save(any());
        verifyNoMoreInteractions(validacion, repo);
    }

    @Test
    void registrarUsuario_propagaError_cuandoValidacionFalla() {

        Usuario u = usuarioConEmail("invalido@correo.com");
        RuntimeException errorValidacion = new RuntimeException("datos_invalidos");
        when(validacion.validate(u)).thenReturn(Mono.error(errorValidacion));

        StepVerifier.create(useCase.ejecutar(u))
                .expectErrorMatches(e -> e == errorValidacion)
                .verify();

        verify(validacion).validate(u);
        verify(repo, never()).existsByEmail(any());
        verify(repo, never()).save(any());
        verifyNoMoreInteractions(validacion, repo);
    }
}
