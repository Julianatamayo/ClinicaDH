package com.clinica.dh.service;

import com.clinica.dh.entity.Paciente;
import java.util.List;
import java.util.Optional;

public interface IPacienteService {
    Paciente guardarPaciente(Paciente paciente);
    Optional<Paciente> encontrarPorId(Integer id);
    List<Paciente> buscarTodos();
    void actualizarPaciente(Paciente paciente);
    void eliminarPaciente(Integer id);
    List<Paciente> buscarPorApellidoyNombre(String apellido, String nombre);
    List<Paciente> buscarPorNombre(String nombre);
}
