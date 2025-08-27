package co.com.pragma.api.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record UsuarioRequestDTO(
        @NotBlank String nombres,
        @NotBlank String apellidos,
        @Email @NotBlank String email,
        @NotNull LocalDate fechaNacimiento,
        @NotNull @DecimalMin(value = "0.0") @DecimalMax(value = "15000000.0") BigDecimal salarioBase,
        @NotBlank String direccion,
        @NotBlank String telefono,
        @NotBlank String rol
) {
}
