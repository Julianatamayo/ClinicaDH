package com.clinica.dh.controller;

import com.clinica.dh.dto.request.TurnoModificarDto;
import com.clinica.dh.dto.request.TurnoRequestDto;
import com.clinica.dh.dto.response.TurnoResponseDto;
import com.clinica.dh.entity.Turno;
import com.clinica.dh.exception.ResourceNotFoundException;
import com.clinica.dh.service.impl.TurnoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    private final TurnoService servicioTurno;
    private static final Logger logger = LoggerFactory.getLogger(TurnoController.class);

    public TurnoController(TurnoService servicioTurno) {
        this.servicioTurno = servicioTurno;
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> registrarTurno(@RequestBody TurnoRequestDto solicitudTurno) {
        logger.info("Intentando registrar un nuevo turno para el paciente: {}", solicitudTurno.getPaciente_id());
        try {
            TurnoResponseDto turnoGuardado = servicioTurno.guardarTurno(solicitudTurno);
            logger.info("Turno registrado correctamente: {}", turnoGuardado.getId());
            return ResponseEntity.ok(turnoGuardado);
        } catch (ResourceNotFoundException e) {
            logger.warn("Error al registrar el turno: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/buscartodos")
    public ResponseEntity<List<TurnoResponseDto>> listarTodosLosTurnos() {
        logger.info("Obteniendo todos los turnos registrados");
        return ResponseEntity.ok(servicioTurno.buscarTodos());
    }

    @PutMapping("/actualizar")
    public ResponseEntity<?> actualizarTurno(@RequestBody TurnoModificarDto turnoModificado) {
        logger.info("Actualizando el turno con ID: {}", turnoModificado.getId());
        try {
            servicioTurno.modificarTurno(turnoModificado);
            logger.info("El turno con ID: {} ha sido modificado correctamente", turnoModificado.getId());
            return ResponseEntity.ok("{\"mensaje\": \"El turno ha sido modificado\"}");
        } catch (ResourceNotFoundException e) {
            logger.warn("Error al modificar el turno: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/buscarporapellido/{apellido}")
    public ResponseEntity<List<TurnoResponseDto>> buscarTurnosPorApellidoPaciente(@PathVariable String apellido) {
        logger.info("Buscando turnos por apellido del paciente: {}", apellido);
        List<Turno> turnos = servicioTurno.buscarTurnoPaciente(apellido);
        List<TurnoResponseDto> turnosDto = turnos.stream()
                .map(turno -> servicioTurno.mapearATurnoResponse(turno))
                .toList();
        return ResponseEntity.ok(turnosDto);
    }

    @GetMapping("/buscarpormatricula/{matricula}")
    public ResponseEntity<List<TurnoResponseDto>> buscarTurnosPorMatriculaOdontologo(@PathVariable String matricula) {
        logger.info("Buscando turnos por matrícula del odontólogo: {}", matricula);
        List<Turno> turnos = servicioTurno.buscarTurnoOdontologo(matricula);
        List<TurnoResponseDto> turnosDto = turnos.stream()
                .map(turno -> servicioTurno.mapearATurnoResponse(turno))
                .toList();
        return ResponseEntity.ok(turnosDto);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable Integer id) {
        logger.info("Intentando eliminar el turno con ID: {}", id);
        try {
            servicioTurno.eliminarTurno(id);
            logger.info("El turno con ID: {} fue eliminado correctamente", id);
            return ResponseEntity.ok("{\"mensaje\": \"El turno fue eliminado\"}");
        } catch (ResourceNotFoundException e) {
            logger.warn("Error al eliminar el turno: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

