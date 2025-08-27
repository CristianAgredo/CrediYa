package co.com.pragma.model;

import co.com.pragma.model.usuario.Usuario;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void builderShouldCreateUsuarioWithAllFields() {
        LocalDate nacimiento = LocalDate.of(1990, 5, 20);
        BigDecimal salario = new BigDecimal("3500000.50");

        Usuario u = Usuario.builder()
                .id(1L)
                .nombres("Juan")
                .apellidos("Pérez")
                .email("juan.perez@correo.com")
                .fechaNacimiento(nacimiento)
                .salarioBase(salario)
                .direccion("Calle 123")
                .telefono("3001234567")
                .rol("CLIENTE")
                .build();

        assertEquals(1L, u.getId());
        assertEquals("Juan", u.getNombres());
        assertEquals("Pérez", u.getApellidos());
        assertEquals("juan.perez@correo.com", u.getEmail());
        assertEquals(nacimiento, u.getFechaNacimiento());
        assertEquals(0, salario.compareTo(u.getSalarioBase())); // compareTo para BigDecimal
        assertEquals("Calle 123", u.getDireccion());
        assertEquals("3001234567", u.getTelefono());
        assertEquals("CLIENTE", u.getRol());
    }

    @Test
    void toBuilderShouldReturnNewInstanceWithUpdatedField() {
        Usuario original = Usuario.builder()
                .id(1L)
                .nombres("Ana")
                .apellidos("Gómez")
                .email("ana.gomez@correo.com")
                .fechaNacimiento(LocalDate.of(1995, 1, 10))
                .salarioBase(new BigDecimal("2500000"))
                .direccion("Av. Siempre Viva")
                .telefono("3010000000")
                .rol("ADMIN")
                .build();

        Usuario modificado = original.toBuilder()
                .email("ana.actualizada@correo.com")
                .build();

        assertNotSame(original, modificado);

        assertEquals("ana.actualizada@correo.com", modificado.getEmail());
        assertEquals(original.getNombres(), modificado.getNombres());
        assertEquals(original.getApellidos(), modificado.getApellidos());
        assertEquals(original.getFechaNacimiento(), modificado.getFechaNacimiento());
        assertEquals(0, original.getSalarioBase().compareTo(modificado.getSalarioBase()));
        assertEquals(original.getDireccion(), modificado.getDireccion());
        assertEquals(original.getTelefono(), modificado.getTelefono());
        assertEquals(original.getRol(), modificado.getRol());
    }

    @Test
    void equalsAndHashCodeShouldBeBasedOnAllFields() {
        Usuario a = Usuario.builder()
                .id(10L)
                .nombres("Carlos")
                .apellidos("López")
                .email("carlos@correo.com")
                .fechaNacimiento(LocalDate.of(1988, 3, 15))
                .salarioBase(new BigDecimal("1800000"))
                .direccion("Cra 45 #10-20")
                .telefono("3021111111")
                .rol("CLIENTE")
                .build();

        Usuario b = Usuario.builder()
                .id(10L)
                .nombres("Carlos")
                .apellidos("López")
                .email("carlos@correo.com")
                .fechaNacimiento(LocalDate.of(1988, 3, 15))
                .salarioBase(new BigDecimal("1800000"))
                .direccion("Cra 45 #10-20")
                .telefono("3021111111")
                .rol("CLIENTE")
                .build();

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());

        Usuario c = b.toBuilder().telefono("3022222222").build();
        assertNotEquals(b, c);
    }

    @Test
    void noArgsConstructorShouldExistAndInitializeFieldsToNull() {
        Usuario u = new Usuario();

        assertNull(u.getId());
        assertNull(u.getNombres());
        assertNull(u.getApellidos());
        assertNull(u.getEmail());
        assertNull(u.getFechaNacimiento());
        assertNull(u.getSalarioBase());
        assertNull(u.getDireccion());
        assertNull(u.getTelefono());
        assertNull(u.getRol());
    }
}
