package com.clinica.odontologica.controller;

import com.clinica.odontologica.dto.request.TurnoModifyDto;
import com.clinica.odontologica.dto.request.TurnoRequestDto;
import com.clinica.odontologica.dto.response.TurnoResponseDto;
import com.clinica.odontologica.entity.Turno;
import com.clinica.odontologica.service.impl.TurnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turno")
public class TurnoController {

    private TurnoService turnoService;

    public TurnoController(TurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> guardarTurno(@RequestBody TurnoRequestDto turnoRequestDto){
        TurnoResponseDto turnoNuevo = turnoService.guardarTurno(turnoRequestDto);
        if (turnoNuevo!= null){
            return ResponseEntity.ok(turnoNuevo);}
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El paciente/odontologo no fueron encontrados");
        }
    }

    @GetMapping("/buscartodos")
    public ResponseEntity<List<TurnoResponseDto>> buscarTodos(){
        return ResponseEntity.ok(turnoService.buscarTodos());
    }

    @PutMapping("/modificar")
    public ResponseEntity<?> modificarTurno(@RequestBody TurnoModifyDto turnoModifyDto) {
        turnoService.modificarTurno(turnoModifyDto);
        String jsonResponse = "{\"mensaje\": \"El turno ha sido modificado\"}";
        return ResponseEntity.ok(jsonResponse);
    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable Integer id){
        turnoService.eliminarTurno(id);
        return ResponseEntity.ok("{\"mensaje\": \"El turno fue eliminado\"}");
    }

    @GetMapping("/buscarApellido/{apellido}")
    public ResponseEntity<List<Turno>> buscarTurnoApellidoPaciente(@PathVariable String apellido){
        return ResponseEntity.ok(turnoService.buscarTurnoPaciente(apellido));
    }

    @GetMapping("/buscarMatricula/{matricula}")
    public ResponseEntity<List<Turno>> buscarTurnoApellidoOdontologo(@PathVariable String matricula){
        return ResponseEntity.ok(turnoService.buscarTurnoOdontologo(matricula));
    }

}
