package com.clinica.dh.controller;

import com.clinica.dh.dto.request.TurnoModificarDto;
import com.clinica.dh.dto.request.TurnoRequestDto;
import com.clinica.dh.dto.response.TurnoResponseDto;
import com.clinica.dh.service.impl.TurnoService;
import com.clinica.dh.entity.Turno;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turnos")
public class TurnoController {
    private TurnoService servicioTurno;

    public TurnoController(TurnoService servicioTurno) {
        this.servicioTurno = servicioTurno;
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> registrarTurno(@RequestBody TurnoRequestDto solicitudTurno) {
        TurnoResponseDto turnoGuardado = servicioTurno.guardarTurno(solicitudTurno);
        if (turnoGuardado != null) {
            return ResponseEntity.ok(turnoGuardado);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El paciente o el odontólogo no fueron encontrados");
        }
    }

    @GetMapping("/buscartodos")
    public ResponseEntity<List<TurnoResponseDto>> listarTodosLosTurnos() {
        return ResponseEntity.ok(servicioTurno.buscarTodos());
    }

    @PutMapping("/modificar")
    public ResponseEntity<?> actualizarTurno(@RequestBody TurnoModificarDto turnoModificado) {
        servicioTurno.modificarTurno(turnoModificado);
        return ResponseEntity.ok("{\"mensaje\": \"El turno ha sido modificado\"}");
    }

    @GetMapping("/buscartodos/{apellido}")
    public ResponseEntity<List<Turno>> buscarTurnosPorApellidoPaciente(@PathVariable String apellido) {
        return ResponseEntity.ok(servicioTurno.buscarTurnoPaciente(apellido));
    }

    @GetMapping("/buscarpormatricula/{matricula}")
    public ResponseEntity<List<Turno>> buscarTurnosPorMatriculaOdontologo(@PathVariable String matricula) {
        return ResponseEntity.ok(servicioTurno.buscarTurnoOdontologo(matricula));
    }
}
