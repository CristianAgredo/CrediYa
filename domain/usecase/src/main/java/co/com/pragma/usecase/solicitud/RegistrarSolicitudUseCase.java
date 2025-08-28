package co.com.pragma.usecase.solicitud;

import co.com.pragma.model.solicitud.*;
import co.com.pragma.model.solicitud.gateways.SolicitudGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.time.Instant;

@RequiredArgsConstructor
public class RegistrarSolicitudUseCase {

    private final SolicitudGateway gateway;

    public Mono<SolicitudPrestamo> ejecutar(SolicitudPrestamo entrada) {

        if (entrada.getMonto() == null || entrada.getMonto().signum() <= 0)
            return Mono.error(new IllegalArgumentException("El monto debe ser mayor a cero"));
        if (entrada.getPlazoMeses() == null || entrada.getPlazoMeses() <= 0)
            return Mono.error(new IllegalArgumentException("El plazo debe ser mayor a 0 meses"));
        if (entrada.getTipo() == null)
            return Mono.error(new IllegalArgumentException("El tipo de préstamo es obligatorio"));

        entrada.setEstado(EstadoSolicitud.PENDIENTE_REVISION);
        entrada.setCreadaEn(Instant.now());

        return gateway.guardar(entrada);
    }
}
