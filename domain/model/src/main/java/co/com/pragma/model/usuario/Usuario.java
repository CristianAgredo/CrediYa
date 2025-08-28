package co.com.pragma.model.usuario;


import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder(toBuilder = true)
public class Usuario {
    private Long id;
    private String nombres;
    private String apellidos;
    private String documento;
    private String email;
    private LocalDate fechaNacimiento;
    private Long salarioBase;
    private String direccion;
    private String telefono;
    private String rol;
}

