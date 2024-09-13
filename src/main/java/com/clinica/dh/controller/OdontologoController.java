package com.clinica.dh.controller;

import com.clinica.dh.entity.Odontologo;
import com.clinica.dh.service.impl.OdontologoService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologo")
public class OdontologoController {
    private OdontologoService servicioOdontologo;

    public OdontologoController(OdontologoService servicioOdontologo) {
        this.servicioOdontologo = servicioOdontologo;
    }

    @PostMapping("/guardar")
    public ResponseEntity<Odontologo> registrarOdontologo(@RequestBody Odontologo nuevoOdontologo) {
        return ResponseEntity.ok(servicioOdontologo.guardarOdontologo(nuevoOdontologo));
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Odontologo> obtenerOdontologoPorId(@PathVariable Integer id) {
        Optional<Odontologo> odontologoEncontrado = servicioOdontologo.encontrarPorId(id);
        if (odontologoEncontrado.isPresent()) {
            return ResponseEntity.ok(odontologoEncontrado.get());
        } else {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
        }
    }

    @GetMapping("/buscartodos")
    public ResponseEntity<List<Odontologo>> listarTodosLosOdontologos() {
        return ResponseEntity.ok(servicioOdontologo.obtenerTodos());
    }

    @PutMapping("/modificar")
    public ResponseEntity<?> actualizarOdontologo(@RequestBody Odontologo odontologoModificado) {
        Optional<Odontologo> odontologoExistente = servicioOdontologo.encontrarPorId(odontologoModificado.getId());
        if (odontologoExistente.isPresent()) {
            servicioOdontologo.actualizarodontolgo(odontologoModificado); // Asegúrate de pasar el objeto modificado
            String respuestaJson = "{\"mensaje\": \"El odontólogo ha sido actualizado correctamente\"}";
            return ResponseEntity.ok(respuestaJson);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> borrarOdontologo(@PathVariable Integer id) {
        Optional<Odontologo> odontologoAEliminar = servicioOdontologo.encontrarPorId(id);
        if (odontologoAEliminar.isPresent()) {
            servicioOdontologo.eliminarodontolgo(id);
            String respuestaJson = "{\"mensaje\": \"El odontólogo ha sido eliminado con éxito\"}";
            return ResponseEntity.ok(respuestaJson);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscarApellidoNombre")
    public ResponseEntity<List<Odontologo>> buscarOdontologoPorApellidoYNombre(@RequestParam String apellido,
                                                                               @RequestParam String nombre) {
        return ResponseEntity.ok(servicioOdontologo.buscarPorApellidoyNombre(apellido, nombre));
    }

    @GetMapping("/buscarNombre/{nombre}")
    public ResponseEntity<List<Odontologo>> buscarOdontologoPorNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(servicioOdontologo.buscarPorNombreSimilar(nombre));
    }

    @GetMapping("/buscarmatricula/{matricula}")
    public ResponseEntity<Optional<Odontologo>> buscarOdontologoPorMatricula(@PathVariable String matricula) {
        Optional<Odontologo> odontologoEncontrado = servicioOdontologo.buscarPorMatricula(matricula);
        if (odontologoEncontrado.isPresent()) {
            return ResponseEntity.ok(servicioOdontologo.buscarPorMatricula(matricula));
        } else {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
        }
    }
}
