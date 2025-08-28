package co.com.pragma.r2dbc.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("usuario")
public class UsuarioEntity {
    @Id
    private Long id;

    private String nombres;
    private String apellidos;
    private String email;

    @Column("fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column("salario_base")
    private BigDecimal salarioBase;

    private String direccion;
    private String telefono;
    private String rol;

}