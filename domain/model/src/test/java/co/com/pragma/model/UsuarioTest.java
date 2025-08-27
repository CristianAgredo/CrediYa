package co.com.pragma.model;

import co.com.pragma.model.usuario.Usuario;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class UsuarioTest {

    @Test
    void crearUsuarioConAllArgsConstructor() {
        BigInteger id = BigInteger.ONE;
        String nombre = "Cristian";
        String apellido = "Agredo";
        String email = "cristian@example.com";
        Date fechaNacimiento = sysdate();
        String documento = "12345678";
        String telefono = "3001234567";
        Long salario = 2500L;
        String rol = "ADMIN";

        Usuario usuario = new Usuario(
                id,
                nombre,
                apellido,
                email ,
                fechaNacimiento,
                telefono,
                rol,
                salario
        );

        assertNotNull(usuario);
        assertEquals(id, usuario.getIdUsuario());
        assertEquals(nombre, usuario.getNombre());
        assertEquals(apellido, usuario.getApellido());
        assertEquals(email, usuario.getEmail());
        assertEquals(documento, usuario.getDocumentoIdentidad());
        assertEquals(telefono, usuario.getTelefono());
        assertEquals(salario, usuario.getSalarioBase());
        assertEquals("ADMIN", usuario.getRol().getNombre());
    }

    @Test
    void crearUsuarioConBuilder() {
        BigInteger id = BigInteger.TWO;
        String nombre = "Ana";
        String apellido = "López";
        String email = "ana@example.com";
        String documento = "87654321";
        String telefono = "3109876543";
        Long salario = 3000L;

        Rol rol = Rol.builder()
                .uniqueId(BigInteger.TWO)
                .nombre("USER")
                .descripcion("Usuario estándar")
                .build();

        Usuario usuario = Usuario.builder()
                .idUsuario(id)
                .nombre(nombre)
                .apellido(apellido)
                .email(email)
                .documentoIdentidad(documento)
                .telefono(telefono)
                .rol(rol)
                .salarioBase(salario)
                .build();

        assertNotNull(usuario);
        assertEquals(id, usuario.getIdUsuario());
        assertEquals(nombre, usuario.getNombre());
        assertEquals(apellido, usuario.getApellido());
        assertEquals(email, usuario.getEmail());
        assertEquals(documento, usuario.getDocumentoIdentidad());
        assertEquals(telefono, usuario.getTelefono());
        assertEquals(salario, usuario.getSalarioBase());
        assertEquals("USER", usuario.getRol().getNombre());
    }

    @Test
    void modificarUsuarioConSetters() {
        String nombre = "Juan";
        Long salario = 2000L;

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setSalarioBase(salario);

        assertEquals(nombre, usuario.getNombre());
        assertEquals(salario, usuario.getSalarioBase());
    }

    @Test
    void usarToBuilderParaModificar() {
        BigInteger id = BigInteger.ONE;
        String nombre = "Carlos";
        Long salario = 2500L;

        Rol rol = new Rol(BigInteger.ONE, "ADMIN", "Administrador");

        Usuario usuario = Usuario.builder()
                .idUsuario(id)
                .nombre(nombre)
                .rol(rol)
                .salarioBase(salario)
                .build();

        String nombreEditado = "Carlos Modificado";
        Long salarioEditado = 4000L;

        Usuario usuarioModificado = usuario.toBuilder()
                .nombre(nombreEditado)
                .salarioBase(salarioEditado)
                .build();

        assertEquals(nombreEditado, usuarioModificado.getNombre());
        assertEquals(salarioEditado, usuarioModificado.getSalarioBase());
        assertEquals(id, usuarioModificado.getIdUsuario()); // conserva el ID
        assertEquals(rol, usuarioModificado.getRol()); // conserva el rol
    }
}
