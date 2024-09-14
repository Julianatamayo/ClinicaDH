package com.clinica.dh.repository;

import com.clinica.dh.entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IOdontologoRepository extends JpaRepository<Odontologo, Integer> {

    List<Odontologo> findByApellidoAndNombre(String apellido, String nombre);

    @Query("Select o from Odontologo o where o.nombre LIKE %:nombre%")
    List<Odontologo> findByNombreLike(String nombre);

    Optional<Odontologo> findByMatricula(String matricula);
}
