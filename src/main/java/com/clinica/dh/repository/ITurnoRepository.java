package com.clinica.dh.repository;

import com.clinica.dh.entity.Paciente;
import com.clinica.dh.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITurnoRepository extends JpaRepository<Turno, Integer> {
    @Query("Select t from Turno t join t.paciente p where p.apellido = :apellidoPaciente")
    List<Turno> buscarTurnoPorApellidoPaciente(String apellidoPaciente);

    @Query("Select t from Turno t join t.odontologo p where p.matricula = :matriculaOdontologo")
    List<Turno> buscarTurnoPorMatriculaOdontologo(String matriculaOdontologo);

}