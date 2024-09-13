package com.clinica.dh.service;

import com.clinica.dh.dto.request.TurnoModificarDto;
import com.clinica.dh.dto.request.TurnoRequestDto;
import com.clinica.dh.dto.response.TurnoResponseDto;
import com.clinica.dh.entity.Turno;

import java.util.List;
import java.util.Optional;

public interface ITurnoService {
    TurnoResponseDto registrarTurno(TurnoRequestDto turnoRequestDto);

    Optional<TurnoResponseDto> encontrarPorId(Integer id);

    List<TurnoResponseDto> obtenerTodos();

    void actualizarTurno(TurnoModificarDto turnoModificarDto);
    void eliminarTurno(Integer id);
    List<Turno> buscarTurnosPorApellidoPaciente(String apellidoPaciente);
    List<Turno> buscarTurnosPorMatriculaOdontologo(String matriculaOdontologo);
}
