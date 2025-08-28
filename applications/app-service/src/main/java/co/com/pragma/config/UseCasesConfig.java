package co.com.pragma.config;

import co.com.pragma.model.usuario.gateways.UsuarioRepositoryGateway;
import co.com.pragma.model.usuario.gateways.UsuarioValidacion;
import co.com.pragma.usecase.usuario.RegistrarUsuarioUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import co.com.pragma.usecase.validaciones.Validaciones;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "co.com.pragma.usecase", includeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")}, useDefaultFilters = false)
public class UseCasesConfig {
    @Bean
    public UsuarioValidacion usuarioValidacion() {
        return new Validaciones();
    }

    @Bean
    public RegistrarUsuarioUseCase registrarUsuarioUseCase(UsuarioRepositoryGateway repo, UsuarioValidacion validacion) {
        return new RegistrarUsuarioUseCase(repo, validacion);
    }
}
