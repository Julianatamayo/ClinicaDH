package com.clinica.dh.service;

import com.clinica.dh.entity.Odontologo;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IOdontologoService {
    Odontologo registrarOdontologo(Odontologo odontologo); // Asegúrate de usar este nombre

    List<Odontologo> obtenerTodos();

    Optional<Odontologo> encontrarPorId(Integer id);

    void actualizarOdontologo(Odontologo odontologo);

    void eliminarOdontologo(Integer id);

    List<Odontologo> buscarPorApellidoyNombre(String apellido, String nombre);

    @Query("Select o from Odontologo o where o.nombre LIKE %:nombre%")
    List<Odontologo> buscarPorNombreSimilar(String nombre);

    Optional<Odontologo> buscarPorMatricula(String matricula);
}

