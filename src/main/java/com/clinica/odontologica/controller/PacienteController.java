package com.clinica.odontologica.controller;


import com.clinica.odontologica.entity.Paciente;
import com.clinica.odontologica.service.impl.PacienteService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/paciente")
public class PacienteController {

    private PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping("/guardar")
    public ResponseEntity<Paciente> guardarPaciente(@RequestBody Paciente paciente){
        return ResponseEntity.ok(pacienteService.guardarPaciente(paciente));
    }


    @GetMapping("/buscar/{id}")
    public ResponseEntity<Paciente> buscarId(@PathVariable Integer id){
        Optional<Paciente> paciente = pacienteService.buscarId(id);
        if (paciente.isPresent()){
            return ResponseEntity.ok(paciente.get());
        }
        else {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
        }
    }

    @GetMapping("/buscartodos")
    public ResponseEntity<List<Paciente>> buscarTodos(){
        return ResponseEntity.ok(pacienteService.buscarTodos());
    }

    @PutMapping("/modificar")
    public ResponseEntity<?> modificarPaciente(@RequestBody Paciente paciente) {
        Optional<Paciente> pacienteEncontrado = pacienteService.buscarId(paciente.getId());
        if (pacienteEncontrado.isPresent()) {
            pacienteService.modificarPaciente(paciente);
            String jsonResponse = "{\"mensaje\": \"El paciente ha sido modificado\"}";
            return ResponseEntity.ok(jsonResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarPaciente(@PathVariable Integer id)
    {Optional<Paciente> pacienteAEliminar = pacienteService.buscarId(id);
        if (pacienteAEliminar.isPresent()){
            pacienteService.eliminarPaciente(id);
            String jsonResponse = "{\"mensaje\": \"El paciente ha sido eliminado\"}";
            return ResponseEntity.ok(jsonResponse);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscarApellido/{apellido}")
    public ResponseEntity<List<Paciente>> buscarApellido(@PathVariable String apellido){
        return ResponseEntity.ok(pacienteService.buscarApellido(apellido));

    }

    @GetMapping("/buscarNombre/{nombre}")
    public ResponseEntity<List<Paciente>> buscarNombre(@PathVariable String nombre){
        return ResponseEntity.ok(pacienteService.buscarNombre(nombre));

    }

    @GetMapping("/buscarApellidoNombre")
    public ResponseEntity<List<Paciente>> buscarApellidoYNombre(@RequestParam String apellido, @RequestParam String nombre){
        return ResponseEntity.ok(pacienteService.buscarPorApellidoyNombre(apellido, nombre));
    }

    @GetMapping("/buscarDni/{dni}")
    public ResponseEntity<Optional<Paciente>> buscarDni(@PathVariable String dni){
        Optional<Paciente> pacienteEncontrado = pacienteService.buscarDni(dni);
        if (pacienteEncontrado.isPresent()){
            return ResponseEntity.ok(pacienteService.buscarDni(dni));
        }
        else {
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).build();
        }
    }


}
