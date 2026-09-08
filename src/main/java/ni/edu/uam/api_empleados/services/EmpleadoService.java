package ni.edu.uam.api_empleados.services;

import ni.edu.uam.api_empleados.dto.EmpleadoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class EmpleadoService {

    private final List<EmpleadoDTO> empleados = new ArrayList<>();
    private final AtomicLong secuencia = new AtomicLong(0);

    public List<EmpleadoDTO> listar() {
        return empleados;
    }

    public Optional<EmpleadoDTO> buscarPorId(Long id) {
        return empleados.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    public EmpleadoDTO guardar(EmpleadoDTO empleado) {
        boolean existe = empleados.stream().anyMatch(e ->
                e.getNombres().trim().equalsIgnoreCase(empleado.getNombres().trim()) &&
                        e.getApellidos().trim().equalsIgnoreCase(empleado.getApellidos().trim()) &&
                        e.getCargo().trim().equalsIgnoreCase(empleado.getCargo().trim())
        );

        if (existe) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe un empleado con los mismos nombres, apellidos y cargo"
            );
        }

        empleado.setId(secuencia.incrementAndGet());
        empleados.add(empleado);
        return empleado;
    }

    public Optional<EmpleadoDTO> actualizar(Long id, EmpleadoDTO datos) {
        return buscarPorId(id).map(empleado -> {
            empleado.setNombres(datos.getNombres());
            empleado.setApellidos(datos.getApellidos());
            empleado.setCargo(datos.getCargo());
            empleado.setSalario(datos.getSalario());
            return empleado;
        });
    }

    public boolean eliminar(Long id) {
        return empleados.removeIf(e -> e.getId().equals(id));
    }
}