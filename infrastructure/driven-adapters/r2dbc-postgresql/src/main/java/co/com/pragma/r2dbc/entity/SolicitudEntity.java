package co.com.pragma.r2dbc.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.Instant;

@Table("solicitudes")
public record SolicitudEntity(
        @Id Long id,
        String documentoIdentidad,
        BigDecimal monto,
        Integer plazoMeses,
        String tipo,
        String estado,
        Instant creadaEn
) {
}
