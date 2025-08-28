// applications/app-service/src/test/java/.../UseCasesConfigTest.java
package co.com.pragma.config;

import co.com.pragma.model.usuario.gateways.UsuarioRepositoryGateway;
import co.com.pragma.usecase.usuario.RegistrarUsuarioUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = UseCasesConfig.class)
class UseCasesConfigTest {

    @MockitoBean
    UsuarioRepositoryGateway gateway; // satisface la dependencia del bean

    @Autowired
    RegistrarUsuarioUseCase useCase;

    @Test
    void testUseCaseBeansExist() {
        assertThat(useCase).isNotNull();
    }
}
