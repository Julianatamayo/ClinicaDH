package com.clinica.dh.repository;

import com.clinica.dh.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPacienteRepository extends JpaRepository<Paciente, Integer> {

    // Busca pacientes por apellido y nombre exacto
    List<Paciente> findByApellidoAndNombre(String apellido, String nombre);

    // Busca pacientes cuyo nombre contenga una parte del nombre (consulta HQL)
    @Query("Select p from Paciente p where p.nombre LIKE %:parteNombre% ")
    List<Paciente> findByNombreLike(String parteNombre);

    // Busca pacientes por nombre exacto
    List<Paciente> findByNombre(String nombre);

    // (Opcional) Busca pacientes cuyo apellido contenga una parte del apellido (consulta HQL adicional)
    @Query("Select p from Paciente p where p.apellido LIKE %:parteApellido%")
    List<Paciente> findByApellidoLike(String parteApellido);
}
