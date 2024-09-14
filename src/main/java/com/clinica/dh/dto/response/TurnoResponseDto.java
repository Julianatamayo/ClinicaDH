package com.clinica.dh.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TurnoResponseDto {
    private Integer id;
    private PacienteResponseDto pacienteResponseDto;
    private OdontologoResponseDto odontologoResponseDto;
    private String fechaTurno; // Nombre del campo actualizado para coincidir con la entidad
}

