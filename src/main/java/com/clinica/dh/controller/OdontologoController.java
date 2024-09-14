package com.clinica.dh.controller;

import com.clinica.dh.entity.Odontologo;
import com.clinica.dh.service.impl.OdontologoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologo")
public class OdontologoController {

    private final OdontologoService servicioOdontologo;
    private static final Logger logger = LoggerFactory.getLogger(OdontologoController.class);

    public OdontologoController(OdontologoService servicioOdontologo) {
        this.servicioOdontologo = servicioOdontologo;
    }

    @PostMapping("/guardar")
    public ResponseEntity<Odontologo> registrarOdontologo(@RequestBody Odontologo nuevoOdontologo) {
        try {
            Odontologo odontologoGuardado = servicioOdontologo.registrarOdontologo(nuevoOdontologo); // Cambiado a registrarOdontologo
            logger.info("Odontólogo guardado con éxito: {}", odontologoGuardado.getId());
            return ResponseEntity.ok(odontologoGuardado);
        } catch (Exception e) {
            logger.error("Error al guardar odontólogo: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Odontologo> obtenerOdontologoPorId(@PathVariable Integer id) {
        Optional<Odontologo> odontologoEncontrado = servicioOdontologo.encontrarPorId(id);
        if (odontologoEncontrado.isPresent()) {
            return ResponseEntity.ok(odontologoEncontrado.get());
        } else {
            logger.warn("Odontólogo con ID: {} no encontrado", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/buscartodos")
    public ResponseEntity<List<Odontologo>> listarTodosLosOdontologos() {
        List<Odontologo> odontologos = servicioOdontologo.obtenerTodos();
        return ResponseEntity.ok(odontologos);
    }

    @PutMapping("/modificar")
    public ResponseEntity<String> actualizarOdontologo(@RequestBody Odontologo odontologoModificado) {
        Optional<Odontologo> odontologoExistente = servicioOdontologo.encontrarPorId(odontologoModificado.getId());
        if (odontologoExistente.isPresent()) {
            try {
                servicioOdontologo.actualizarOdontologo(odontologoModificado); // Cambiado a actualizarOdontologo
                logger.info("Odontólogo actualizado correctamente: {}", odontologoModificado.getId());
                return ResponseEntity.ok("{\"mensaje\": \"El odontólogo ha sido actualizado correctamente\"}");
            } catch (Exception e) {
                logger.error("Error al actualizar odontólogo: {}", e.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } else {
            logger.warn("Odontólogo con ID: {} no encontrado para actualización", odontologoModificado.getId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> borrarOdontologo(@PathVariable Integer id) {
        Optional<Odontologo> odontologoAEliminar = servicioOdontologo.encontrarPorId(id);
        if (odontologoAEliminar.isPresent()) {
            try {
                servicioOdontologo.eliminarOdontologo(id); // Cambiado a eliminarOdontologo
                logger.info("Odontólogo con ID: {} eliminado correctamente", id);
                return ResponseEntity.ok("{\"mensaje\": \"El odontólogo ha sido eliminado con éxito\"}");
            } catch (Exception e) {
                logger.error("Error al eliminar odontólogo: {}", e.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } else {
            logger.warn("Odontólogo con ID: {} no encontrado para eliminación", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/buscarApellidoNombre")
    public ResponseEntity<List<Odontologo>> buscarOdontologoPorApellidoYNombre(@RequestParam String apellido,
                                                                               @RequestParam String nombre) {
        List<Odontologo> odontologos = servicioOdontologo.buscarPorApellidoyNombre(apellido, nombre);
        return ResponseEntity.ok(odontologos);
    }

    @GetMapping("/buscarNombre/{nombre}")
    public ResponseEntity<List<Odontologo>> buscarOdontologoPorNombre(@PathVariable String nombre) {
        List<Odontologo> odontologos = servicioOdontologo.buscarPorNombreSimilar(nombre);
        return ResponseEntity.ok(odontologos);
    }

    @GetMapping("/buscarmatricula/{matricula}")
    public ResponseEntity<Odontologo> buscarOdontologoPorMatricula(@PathVariable String matricula) {
        Optional<Odontologo> odontologoEncontrado = servicioOdontologo.buscarPorMatricula(matricula);
        if (odontologoEncontrado.isPresent()) {
            return ResponseEntity.ok(odontologoEncontrado.get());
        } else {
            logger.warn("Odontólogo con matrícula: {} no encontrado", matricula);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
