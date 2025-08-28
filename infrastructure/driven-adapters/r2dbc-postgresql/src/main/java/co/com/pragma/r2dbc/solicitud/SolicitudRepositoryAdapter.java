package co.com.pragma.r2dbc.solicitud;

import co.com.pragma.model.solicitud.*;
        import co.com.pragma.model.solicitud.gateways.SolicitudGateway;
import co.com.pragma.r2dbc.entity.SolicitudEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class SolicitudRepositoryAdapter implements SolicitudGateway {

    private final SolicitudReactiveRepository repo;

    @Override
    public Mono<SolicitudPrestamo> guardar(SolicitudPrestamo s) {
        SolicitudEntity e = new SolicitudEntity(
                s.getId(), s.getDocumentoIdentidad(), s.getMonto(), s.getPlazoMeses(),
                s.getTipo().name(), s.getEstado().name(), s.getCreadaEn()
        );
        return repo.save(e)
                .map(x -> SolicitudPrestamo.builder()
                        .id(x.id())
                        .documentoIdentidad(x.documentoIdentidad())
                        .monto(x.monto())
                        .plazoMeses(x.plazoMeses())
                        .tipo(TipoPrestamo.valueOf(x.tipo()))
                        .estado(EstadoSolicitud.valueOf(x.estado()))
                        .creadaEn(x.creadaEn())
                        .build());
    }
}
