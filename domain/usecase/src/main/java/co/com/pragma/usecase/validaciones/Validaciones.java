package co.com.pragma.usecase.validaciones;

import co.com.pragma.model.usuario.Usuario;
import co.com.pragma.model.usuario.ValidationException;
import co.com.pragma.model.usuario.gateways.UsuarioValidacion;
import reactor.core.publisher.Mono;

public class Validaciones implements UsuarioValidacion {

    String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    @Override
    public Mono<Void> validate(Usuario usuario) {
        if (usuario.getNombres() == null || usuario.getNombres().isEmpty()) {
            return Mono.error(new ValidationException("El campo nombre no puede ser nulo o vacío"));
        } else if (usuario.getDocumento() == null || usuario.getDocumento().isEmpty()) {
            return Mono.error(new ValidationException("El campo documento no puede ser nulo o vacío"));
        } else if (!usuario.getEmail().matches(emailRegex)) {
            return Mono.error(new ValidationException("El campo correo_electronico debe tener un formato de email válido"));
        } else if (usuario.getApellidos() == null || usuario.getApellidos().isEmpty()) {
            return Mono.error(new ValidationException("El campo apellido no puede ser nulo o vacío"));
        }else if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            return Mono.error(new ValidationException("El campo email no puede ser nulo o vacío"));
        } else if (usuario.getSalarioBase() == null || usuario.getSalarioBase() <= 0) {
            return Mono.error(new ValidationException("El campo salario base debe ser mayor a cero y no puede ser nulo"));
        } else if (usuario.getSalarioBase() > 15000000) {
            return Mono.error(new ValidationException("El campo salario_base debe ser un valor numérico entre 0 y 15000000"));
        }
        return Mono.empty();
    }

}
