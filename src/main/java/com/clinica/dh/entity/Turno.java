package com.clinica.dh.entity;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="turnos")
public class Turno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JsonBackReference(value = "paciente-turno")
    @NotNull(message = "Paciente no puede ser nulo")
    private Paciente paciente;

    @ManyToOne
    @JsonBackReference(value = "odontologo-turno")
    @NotNull(message = "Odontólogo no puede ser nulo")
    private Odontologo odontologo;

    @NotNull(message = "La fecha del turno no puede ser nula")
    @Future(message = "La fecha del turno debe ser una fecha futura")
    private LocalDate fechaTurno;

    @Override
    public String toString() {
        return "Turno{" +
                "id=" + id +
                ", paciente=" + paciente +
                ", odontologo=" + odontologo +
                ", fechaTurno=" + fechaTurno +
                '}';
    }
}


