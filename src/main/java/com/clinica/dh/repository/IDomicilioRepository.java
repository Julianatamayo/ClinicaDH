package com.clinica.dh.repository;


import com.clinica.dh.entity.Domicilio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDomicilioRepository extends JpaRepository <Domicilio, Integer>{
}
