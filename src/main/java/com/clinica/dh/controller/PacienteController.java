package com.clinica.dh.controller;

import com.clinica.dh.entity.Paciente;
import com.clinica.dh.service.impl.PacienteService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/paciente")
public class PacienteController {
    private PacienteService servicioPaciente;

    public PacienteController(PacienteService servicioPaciente) {
        this.servicioPaciente = servicioPaciente;
    }

    @PostMapping("/guardar")
    public ResponseEntity<Paciente> registrarPaciente(@RequestBody Paciente nuevoPaciente) {
        return ResponseEntity.ok(servicioPaciente.guardarPaciente(nuevoPaciente));
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> obtenerPacientePorId(@PathVariable Integer id) {
        Optional<Paciente> pacienteEncontrado = servicioPaciente.encontrarPorId(id);
        if (pacienteEncontrado.isPresent()) {
            return ResponseEntity.ok(pacienteEncontrado.get());
        } else {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
        }
    }

    @GetMapping("/buscartodos")
    public ResponseEntity<List<Paciente>> listarTodosLosPacientes() {
        return ResponseEntity.ok(servicioPaciente.buscarTodos());
    }

    @PutMapping("/modificar")
    public ResponseEntity<?> actualizarPaciente(@RequestBody Paciente pacienteModificado) {
        Optional<Paciente> pacienteExistente = servicioPaciente.encontrarPorId(pacienteModificado.findById());
        if (pacienteExistente.isPresent()) {
            servicioPaciente.actualizarPaciente(pacienteExistente.get());
            String respuestaJson = "{\"mensaje\": \"El paciente ha sido actualizado correctamente\"}";
            return ResponseEntity.ok(respuestaJson);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarPaciente(@PathVariable Integer id) {
        Optional<Paciente> pacienteAEliminar = servicioPaciente.encontrarPorId(id);
        if (pacienteAEliminar.isPresent()) {
            servicioPaciente.eliminarPaciente(id);
            String respuestaJson = "{\"mensaje\": \"El paciente ha sido eliminado con éxito\"}";
            return ResponseEntity.ok(respuestaJson);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscarApellidoNombre")
    public ResponseEntity<List<Paciente>> buscarPacientePorApellidoYNombre(@RequestParam String apellido,
                                                                           @RequestParam String nombre) {
        return ResponseEntity.ok(servicioPaciente.buscarPorApellidoyNombre(apellido, nombre));
    }

    @GetMapping("/buscarNombre/{nombre}")
    public ResponseEntity<List<Paciente>> buscarPacientePorNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(servicioPaciente.buscarPorNombre(nombre));
    }
}
