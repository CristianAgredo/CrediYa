package co.com.pragma.api;

import co.com.pragma.api.dto.UsuarioRequestDTO;
import co.com.pragma.model.usuario.Usuario;
import co.com.pragma.usecase.usuario.RegistrarUsuarioUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import jakarta.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class Handler {

    private final RegistrarUsuarioUseCase useCase;
    private final Validator validator;

    @Operation(
            summary = "Crear usuario",
            description = "Registra un nuevo usuario validando reglas de negocio",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Creado",
                            content = @Content(schema = @Schema(implementation = Usuario.class))),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos"),
                    @ApiResponse(responseCode = "409", description = "Email existente")
            }
    )
    public Mono<ServerResponse> crearUsuario(ServerRequest req) {
        return req.bodyToMono(UsuarioRequestDTO.class)
                .flatMap(this::validate)
                .map(this::toDomain)
                .flatMap(useCase::ejecutar)
                .flatMap(u -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(u))
                .onErrorResume(ConstraintViolationException.class, this::badRequest);
    }

    private <T> Mono<T> validate(T body) {
        Set<ConstraintViolation<T>> errors = validator.validate(body);
        return errors.isEmpty() ? Mono.just(body) : Mono.error(new ConstraintViolationException(errors));
    }

    private Mono<ServerResponse> badRequest(ConstraintViolationException ex) {
        var mensajes = ex.getConstraintViolations().stream()
                .map(cv -> cv.getPropertyPath() + ": " + cv.getMessage())
                .toList();
        return ServerResponse.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(mensajes);
    }

    private Usuario toDomain(UsuarioRequestDTO dto) {
        return Usuario.builder()
                .nombres(dto.nombres())
                .apellidos(dto.apellidos())
                .email(dto.email())
                .fechaNacimiento(dto.fechaNacimiento())
                .salarioBase(dto.salarioBase())
                .direccion(dto.direccion())
                .telefono(dto.telefono())
                .rol(dto.rol())
                .build();
    }
}
