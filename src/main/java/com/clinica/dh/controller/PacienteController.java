package com.clinica.dh.controller;

import com.clinica.dh.entity.Paciente;
import com.clinica.dh.service.impl.PacienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/paciente")
public class PacienteController {

    private final PacienteService servicioPaciente;
    private static final Logger logger = LoggerFactory.getLogger(PacienteController.class);

    public PacienteController(PacienteService servicioPaciente) {
        this.servicioPaciente = servicioPaciente;
    }

    @PostMapping("/guardar")
    public ResponseEntity<Paciente> registrarPaciente(@RequestBody Paciente nuevoPaciente) {
        logger.info("Intentando registrar un nuevo paciente: {}", nuevoPaciente.getNombre());
        Paciente pacienteGuardado = servicioPaciente.guardarPaciente(nuevoPaciente);
        return ResponseEntity.ok(pacienteGuardado);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> obtenerPacientePorId(@PathVariable Integer id) {
        logger.info("Buscando paciente con ID: {}", id);
        Optional<Paciente> pacienteEncontrado = servicioPaciente.encontrarPorId(id);
        if (pacienteEncontrado.isPresent()) {
            logger.info("Paciente encontrado: {}", pacienteEncontrado.get().getNombre());
            return ResponseEntity.ok(pacienteEncontrado.get());
        } else {
            logger.warn("Paciente con ID: {} no encontrado", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"Paciente no encontrado\"}");
        }
    }

    @GetMapping("/buscartodos")
    public ResponseEntity<List<Paciente>> listarTodosLosPacientes() {
        logger.info("Listando todos los pacientes");
        List<Paciente> pacientes = servicioPaciente.buscarTodos();
        return ResponseEntity.ok(pacientes);
    }

    @PutMapping("/modificar")
    public ResponseEntity<?> actualizarPaciente(@RequestBody Paciente pacienteModificado) {
        logger.info("Intentando modificar el paciente con ID: {}", pacienteModificado.getId());
        Optional<Paciente> pacienteExistente = servicioPaciente.encontrarPorId(pacienteModificado.getId());
        if (pacienteExistente.isPresent()) {
            servicioPaciente.actualizarPaciente(pacienteModificado);
            logger.info("Paciente con ID: {} modificado correctamente", pacienteModificado.getId());
            return ResponseEntity.ok("{\"mensaje\": \"El paciente ha sido actualizado correctamente\"}");
        } else {
            logger.warn("Paciente con ID: {} no encontrado para modificar", pacienteModificado.getId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"Paciente no encontrado\"}");
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarPaciente(@PathVariable Integer id) {
        logger.info("Intentando eliminar el paciente con ID: {}", id);
        Optional<Paciente> pacienteAEliminar = servicioPaciente.encontrarPorId(id);
        if (pacienteAEliminar.isPresent()) {
            servicioPaciente.eliminarPaciente(id);
            logger.info("Paciente con ID: {} eliminado correctamente", id);
            return ResponseEntity.ok("{\"mensaje\": \"El paciente ha sido eliminado con éxito\"}");
        } else {
            logger.warn("Paciente con ID: {} no encontrado para eliminar", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"Paciente no encontrado\"}");
        }
    }

    @GetMapping("/buscarApellidoNombre")
    public ResponseEntity<List<Paciente>> buscarPacientePorApellidoYNombre(@RequestParam String apellido, @RequestParam String nombre) {
        logger.info("Buscando pacientes por apellido: {} y nombre: {}", apellido, nombre);
        List<Paciente> pacientes = servicioPaciente.buscarPorApellidoyNombre(apellido, nombre);
        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/buscarNombre/{nombre}")
    public ResponseEntity<List<Paciente>> buscarPacientePorNombre(@PathVariable String nombre) {
        logger.info("Buscando pacientes por nombre: {}", nombre);
        List<Paciente> pacientes = servicioPaciente.buscarPorNombre(nombre);
        return ResponseEntity.ok(pacientes);
    }
}

