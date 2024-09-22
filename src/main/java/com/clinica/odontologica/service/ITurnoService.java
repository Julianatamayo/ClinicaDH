package com.clinica.odontologica.service;

import com.clinica.odontologica.dto.request.TurnoModifyDto;
import com.clinica.odontologica.dto.request.TurnoRequestDto;
import com.clinica.odontologica.dto.response.TurnoResponseDto;
import com.clinica.odontologica.entity.Turno;

import java.util.List;
import java.util.Optional;


public interface ITurnoService {
    TurnoResponseDto guardarTurno(TurnoRequestDto turnoRequestDto);

    Optional<TurnoResponseDto> buscarId(Integer id);

    List<TurnoResponseDto> buscarTodos();

    void modificarTurno(TurnoModifyDto turnoModificarDto);
    void eliminarTurno(Integer id);

    List<Turno> buscarTurnoPaciente(String apellidoPaciente);

    List<Turno> buscarTurnoOdontologo(String matriculaOdontologo);

}
