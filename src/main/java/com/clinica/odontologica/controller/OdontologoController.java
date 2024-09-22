package com.clinica.odontologica.controller;


import com.clinica.odontologica.entity.Odontologo;
import com.clinica.odontologica.service.impl.OdontologoService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologo")
public class OdontologoController {

    private OdontologoService odontologoService;
    public OdontologoController(OdontologoService odontologoService) {
        this.odontologoService = odontologoService;

    }
    @PostMapping("/guardar")
    public ResponseEntity<Odontologo> guardarOdontologo(@RequestBody Odontologo odontologo){
        return ResponseEntity.ok(odontologoService.guardarOdontologo(odontologo));
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Odontologo> buscarId(@PathVariable Integer id){
        Optional<Odontologo> odontologo = odontologoService.buscarId(id);
        if (odontologo.isPresent()){
            return ResponseEntity.ok(odontologo.get());
        }
        else {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
        }
    }

    @GetMapping("/buscartodos")
    public ResponseEntity<List<Odontologo>> buscartodos(){
        return ResponseEntity.ok(odontologoService.buscarTodos());
    }

    @PutMapping("/modificar")
    public ResponseEntity<?> modificarOdontologo(@RequestBody Odontologo odontologo) {
        Optional<Odontologo> odontologoEncontrado = odontologoService.buscarId(odontologo.getId());
        if (odontologoEncontrado.isPresent()) {
            odontologoService.modificarOdontologo(odontologo);
            String jsonResponse = "{\"mensaje\": \"El odontólogo ha sido modificado\"}";
            return ResponseEntity.ok(jsonResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarOdontologo(@PathVariable Integer id)
    {Optional<Odontologo> odontologoAEliminar = odontologoService.buscarId(id);
        if (odontologoAEliminar.isPresent()){
            odontologoService.eliminarOdontologo(id);
            String jsonResponse = "{\"mensaje\": \"El odontólogo ha sido eliminado\"}";
            return ResponseEntity.ok(jsonResponse);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscarApellido/{apellido}")
    public ResponseEntity<List<Odontologo>> buscarApellido(@PathVariable String apellido){
        return ResponseEntity.ok(odontologoService.buscarApellido(apellido));

    }

    @GetMapping("/buscarNombre/{nombre}")
    public ResponseEntity<List<Odontologo>> buscarNombre(@PathVariable String nombre){
        return ResponseEntity.ok(odontologoService.buscarNombre(nombre));

    }

    @GetMapping("/buscarApellidoNombre")
    public ResponseEntity<List<Odontologo>> buscarApellidoYNombre(@RequestParam String apellido, @RequestParam String nombre){
        return ResponseEntity.ok(odontologoService.buscarPorApellidoyNombre(apellido, nombre));
    }

    @GetMapping("/buscarMatricula/{matricula}")
    public ResponseEntity<Optional<Odontologo>> buscarMatricula(@PathVariable String matricula){
        Optional<Odontologo> odontologoEncontrado = odontologoService.buscarMatricula(matricula);
        if (odontologoEncontrado.isPresent()){
            return ResponseEntity.ok(odontologoService.buscarMatricula(matricula));
        }
        else {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
        }
    }
}

