package com.clinica.dh.service;

import com.clinica.dh.dto.request.TurnoModificarDto;
import com.clinica.dh.dto.request.TurnoRequestDto;
import com.clinica.dh.dto.response.TurnoResponseDto;
import com.clinica.dh.entity.Turno;

import java.util.List;
import java.util.Optional;

public interface ITurnoService {
    TurnoResponseDto guardarTurno(TurnoRequestDto turnoRequestDto);
    Optional<TurnoResponseDto> buscarPorId(Integer id);
    List<TurnoResponseDto> buscarTodos();
    void modificarTurno(TurnoModificarDto turnoModificarDto);
    void eliminarTurno(Integer id);
    List<Turno> buscarTurnoPaciente(String apellidoPaciente);
    List<Turno> buscarTurnoOdontologo(String matriculaOdontologo);
}

