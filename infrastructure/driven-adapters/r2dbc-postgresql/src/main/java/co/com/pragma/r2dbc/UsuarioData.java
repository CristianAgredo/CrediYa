package co.com.pragma.r2dbc;

import co.com.pragma.r2dbc.helper.ReactiveAdapterOperations;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("usuario")
public class UsuarioData {
    @Id
    private Long id;
    private String nombres;
    private String apellidos;
    private String email;
    private LocalDate fecha_nacimiento;
    private BigDecimal salario_base;
    private String direccion;
    private String telefono;
    private String rol;
}