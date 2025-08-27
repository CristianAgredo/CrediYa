package co.com.pragma.usecase;

import co.com.pragma.model.usuario.Usuario;
import co.com.pragma.model.usuario.gateways.UsuarioRepositoryGateway;
import co.com.pragma.usecase.usuario.RegistrarUsuarioUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RegistrarUsuarioUseCaseTest {

    private final UsuarioRepositoryGateway repo = Mockito.mock(UsuarioRepositoryGateway.class);
    private final RegistrarUsuarioUseCase useCase = new RegistrarUsuarioUseCase(repo);

    @Test
    void registraUsuario_ok() {
        Usuario input = Usuario.builder()
                .nombres("Cristian")
                .apellidos("Agredo")
                .email("cristian@example.com")
                .fechaNacimiento(LocalDate.of(2000,7,18))
                .salarioBase(new BigDecimal("1200000.00"))
                .direccion("Cali")
                .telefono("3001234567")
                .rol("ADMIN")
                .build();

        when(repo.existsByEmail("cristian@example.com")).thenReturn(Mono.just(false));
        when(repo.save(any())).thenAnswer(i -> Mono.just(i.getArgument(0)));

        StepVerifier.create(useCase.ejecutar(input))
                .expectNextMatches(u -> u.getEmail().equals("cristian@example.com"))
                .verifyComplete();

        verify(repo).existsByEmail("cristian@example.com");
        verify(repo).save(any(Usuario.class));
    }

    @Test
    void registraUsuario_emailDuplicado() {
        Usuario input = Usuario.builder().email("dup@example.com").build();

        when(repo.existsByEmail("dup@example.com")).thenReturn(Mono.just(true));

        StepVerifier.create(useCase.ejecutar(input))
                .expectErrorMatches(ex -> ex.getMessage().toLowerCase().contains("ya está registrado"))
                .verify();

        verify(repo, never()).save(any());
    }
}
