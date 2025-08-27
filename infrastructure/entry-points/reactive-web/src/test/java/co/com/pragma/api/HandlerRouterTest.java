package co.com.pragma.api;

import co.com.pragma.api.dto.UsuarioRequestDTO;
import co.com.pragma.model.usuario.Usuario;
import co.com.pragma.usecase.usuario.RegistrarUsuarioUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;

@WebFluxTest
@Import({RouterRest.class, HandlerRouterTest.TestConfig.class, Handler.class})
class HandlerRouterTest {

    @Autowired
    WebTestClient client;

    @Autowired
    RegistrarUsuarioUseCase useCase;

    @BeforeEach
    void setup() {
        Mockito.reset(useCase);
    }

    @Test
    void creaUsuario_201() {
        var request = new UsuarioRequestDTO(
                "Cristian","Agredo","cristian@example.com",
                LocalDate.of(2000,7,18), new BigDecimal("1200000.00"),
                "Cali","3001234567","ADMIN"
        );

        Mockito.when(useCase.ejecutar(any(Usuario.class)))
                .thenAnswer(inv -> Mono.just(inv.getArgument(0)));

        client.post().uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON);
    }

    @Test
    void creaUsuario_400_emailInvalido() {
        var request = new UsuarioRequestDTO(
                "Nombre","Apellido","correo-malo",
                LocalDate.of(2000,7,18), new BigDecimal("16000000.00"), // viola regla de max
                "Cali","3001234567","ADMIN"
        );

        client.post().uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$[0]").exists();
    }

    static class TestConfig {


        @Bean
        LocalValidatorFactoryBean validator() {
            return new LocalValidatorFactoryBean();
        }


        @Bean
        RegistrarUsuarioUseCase registrarUsuarioUseCase() {
            return Mockito.mock(RegistrarUsuarioUseCase.class);
        }
    }
}
