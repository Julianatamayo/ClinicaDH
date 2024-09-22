package com.clinica.odontologica.controller;

import com.clinica.odontologica.entity.Odontologo;
import com.clinica.odontologica.entity.Paciente;
import com.clinica.odontologica.service.impl.PacienteService;
import com.clinica.odontologica.service.impl.OdontologoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class VistaController {
    private PacienteService pacienteService;
    private OdontologoService odontologoService;

    public VistaController(PacienteService pacienteService, OdontologoService odontologoService) {
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }

    // localhost:8080/20  -> @PathVariable
    // localhost:8080?id=1  -> @RequestParams
    @GetMapping("/index")
    public String mostrarPacientePorId(Model model, @RequestParam Integer id){
        Paciente paciente = pacienteService.buscarId(id).get();
        model.addAttribute("nombre", paciente.getNombre());
        model.addAttribute("apellido", paciente.getApellido());
        return "paciente";
    }

    @GetMapping("/index2/{id}")
    public String mostrarPacientePorId2(Model model, @PathVariable Integer id){
        Paciente paciente = pacienteService.buscarId(id).get();
        model.addAttribute("nombre", paciente.getNombre());
        model.addAttribute("apellido", paciente.getApellido());
        return "paciente";
    }

    @GetMapping("/odontologos")
    public String mostrarOdontologosPorId(Model model, @RequestParam Integer id){
        Odontologo odontologo = odontologoService.buscarId(id).get();
        model.addAttribute("nombre", odontologo.getNombre());
        model.addAttribute("apellido", odontologo.getApellido());
        return "odontologo";
    }
}

