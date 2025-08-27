package co.com.pragma.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;

import co.com.pragma.model.usuario.Usuario;
import org.junit.jupiter.api.Test;

public class UsuarioTest {

    @Test
    void debeConstruirUsuarioConBuilder() {
        Long id = null;
        String nombre = "Juan";
        String apellido = "Pérez";
        String email = "juan@correo.com";
        String fecha = "2000-07-18";
        String telefono = "3001234567";
        BigDecimal salario = new BigDecimal("2000000");
        String rol = "USER";

        Usuario usuario = Usuario.builder()
                .id(null)
                .nombres(nombre)
                .apellidos(apellido)
                .email(email)
                .fechaNacimiento(LocalDate.parse(fecha))
                .telefono(telefono)
                .salarioBase(salario)
                .rol(rol)
                .build();

        assertEquals(id, usuario.getId());
        assertEquals(nombre, usuario.getNombres());
        assertEquals(apellido, usuario.getApellidos());
        assertEquals(email, usuario.getEmail());
        assertEquals(fecha, usuario.getFechaNacimiento());
        assertEquals(telefono, usuario.getTelefono());
        assertEquals(salario, usuario.getSalarioBase());
        assertEquals("USER", usuario.getRol());
    }

    @Test
    void debePermitirModificarCampos() {
        Usuario usuario = new Usuario();
        usuario.setNombres("Ana");
        usuario.setSalarioBase(new BigDecimal("1500000"));

        assertEquals("Ana", usuario.getNombres());
        assertEquals(new BigDecimal("1500000"), usuario.getSalarioBase());
    }

    @Test
    void debePermitirEditarConBuilderToBuilder() {
        Long id = Long.valueOf(1);

        Usuario original = Usuario.builder()
                .id(id)
                .nombres("Pedro")
                .salarioBase(new BigDecimal("1000000"))
                .rol("ADMIN")
                .build();

        Usuario modificado = original.toBuilder()
                .nombres("Pedro Editado")
                .salarioBase(new BigDecimal("2500000"))
                .build();

        assertEquals("Pedro Editado", modificado.getNombres());
        assertEquals(new BigDecimal("2500000"), modificado.getSalarioBase());
        assertEquals(id, modificado.getId());
    }
}
