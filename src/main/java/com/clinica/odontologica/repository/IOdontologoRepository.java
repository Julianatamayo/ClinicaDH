package com.clinica.odontologica.repository;

import com.clinica.odontologica.entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IOdontologoRepository extends JpaRepository<Odontologo, Integer>{

    @Query("SELECT o FROM Odontologo o WHERE o.nombre LIKE %:nombre%")
    List<Odontologo> findByNombre(String nombre);

    @Query("SELECT o FROM Odontologo o WHERE o.apellido LIKE %:apellido%")
    List<Odontologo> findByApellido(String apellido);

    Optional<Odontologo> findByMatricula(String matricula);

    List<Odontologo> findByApellidoAndNombre(String apellido, String nombre);



}
