package com.clinica.dh.controller;

import com.clinica.dh.entity.Paciente;
import com.clinica.dh.service.impl.PacienteService;
import com.clinica.dh.entity.Odontologo;
import com.clinica.dh.service.impl.OdontologoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VistaController {
    private PacienteService servicioPaciente;
    private OdontologoService servicioOdontologo;

    public VistaController(PacienteService servicioPaciente, OdontologoService servicioOdontologo) {
        this.servicioPaciente = servicioPaciente;
        this.servicioOdontologo = servicioOdontologo;
    }

    @GetMapping("/index")
    public String mostrarDetallesPaciente(Model modelo, @RequestParam Integer idPaciente) {
        Paciente pacienteSeleccionado = servicioPaciente.encontrarPorId(idPaciente).get();
        modelo.addAttribute("nombre", pacienteSeleccionado.getNombre());
        modelo.addAttribute("apellido", pacienteSeleccionado.getApellido());
        return "paciente";
    }

    @GetMapping("/index2/{id}")
    public String mostrarDetallesPacienteConPath(Model modelo, @PathVariable Integer id) {
        Paciente pacienteSeleccionado = servicioPaciente.encontrarPorId(id).get();
        modelo.addAttribute("nombre", pacienteSeleccionado.getNombre());
        modelo.addAttribute("apellido", pacienteSeleccionado.getApellido());
        return "paciente";
    }

    @GetMapping("/odontologos")
    public String mostrarDetallesOdontologo(Model modelo, @RequestParam Integer idOdontologo) {
        Odontologo odontologoSeleccionado = servicioOdontologo.encontrarPorId(idOdontologo).get();
        modelo.addAttribute("nombre", odontologoSeleccionado.getNombre());
        modelo.addAttribute("apellido", odontologoSeleccionado.getApellido());
        return "odontologo";
    }
}
