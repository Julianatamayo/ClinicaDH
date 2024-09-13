package com.clinica.dh.service;

import com.clinica.dh.entity.Paciente;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IPacienteService {
    Paciente registrarPaciente(Paciente paciente);

    Optional<Paciente> encontrarPorId(Integer id);

    List<Paciente> obtenerTodos();

    void actualizarPaciente(Paciente paciente);

    void eliminarPaciente(Integer id);

    List<Paciente> buscarPorApellidoyNombre(String apellido, String nombre);

    @Query("Select p from Paciente p where p.nombre LIKE %:nombre%")
    List<Paciente> buscarPorNombreSimilar(String nombre);


    // select * from pacientes where nombre like CONCAT('%',variable,'%');
}
