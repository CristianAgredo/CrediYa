package co.com.pragma.model.solicitud.gateways;

import co.com.pragma.model.solicitud.SolicitudPrestamo;
import reactor.core.publisher.Mono;

public interface SolicitudGateway {
    Mono<SolicitudPrestamo> guardar(SolicitudPrestamo solicitud);
}
