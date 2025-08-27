package co.com.pragma.api;

import co.com.pragma.api.dto.UsuarioRequestDTO;
import co.com.pragma.model.usuario.Usuario;
import co.com.pragma.usecase.usuario.RegistrarUsuarioUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

@ContextConfiguration(classes = {RouterRest.class, Handler.class})
@WebFluxTest(controllers = Handler.class)
@Import({RouterRest.class})
class RouterRestTest {

    @Autowired
    WebTestClient webTestClient;

    @MockitoBean
    RegistrarUsuarioUseCase useCase;

    @Test
    void testCrearUsuario_ok() {
        UsuarioRequestDTO req = new UsuarioRequestDTO(
                "Cristian","Agredo","cristian@example.com",
                LocalDate.of(2000,7,18), new BigDecimal("1000000"),
                "Cali","3001234567","ADMIN"
        );

        Usuario resp = Usuario.builder()
                .id(1L)
                .nombres(req.nombres())
                .apellidos(req.apellidos())
                .email(req.email())
                .fechaNacimiento(req.fechaNacimiento())
                .salarioBase(req.salarioBase())
                .direccion(req.direccion())
                .telefono(req.telefono())
                .rol(req.rol())
                .build();

        Mockito.when(useCase.ejecutar(Mockito.any())).thenReturn(Mono.just(resp));

        webTestClient.post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.email").isEqualTo("cristian@example.com");
    }
}
