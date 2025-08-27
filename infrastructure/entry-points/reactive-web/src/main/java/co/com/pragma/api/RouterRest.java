package co.com.pragma.api;

import co.com.pragma.model.usuario.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterRest {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/api/v1/usuarios",
                    method = { },
                    beanClass = Handler.class,
                    beanMethod = "crearUsuario",
                    operation = @Operation(
                            summary = "Crear usuario",
                            description = "Registra un nuevo usuario validando reglas de negocio",
                            responses = {
                                    @ApiResponse(responseCode = "201", description = "Creado",
                                            content = @Content(schema = @Schema(implementation = Usuario.class))),
                                    @ApiResponse(responseCode = "400", description = "Datos inválidos"),
                                    @ApiResponse(responseCode = "409", description = "Email existente")
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> routes(Handler h) {
        return RouterFunctions.route()
                .POST("/api/v1/usuarios", h::crearUsuario)
                .build();
    }
}
