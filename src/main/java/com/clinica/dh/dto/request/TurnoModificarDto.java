package com.clinica.dh.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TurnoModificarDto {
    private Integer id;

    private Integer paciente_id;


    private Integer odontologo_id;


    private String fecha;
}
