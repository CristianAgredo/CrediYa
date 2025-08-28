package co.com.pragma.model.solicitud;

import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class SolicitudPrestamo {
    private Long id;
    private String documentoIdentidad;
    private BigDecimal monto;
    private Integer plazoMeses;
    private TipoPrestamo tipo;
    private EstadoSolicitud estado;
    private Instant creadaEn;
}
