package com.clinica.dh.service.impl;


import com.clinica.dh.entity.Odontologo;
import com.clinica.dh.repository.IOdontologoRepository;
import com.clinica.dh.service.IOdontologoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService implements IOdontologoService {

    private IOdontologoRepository odontologoRepository;

    public OdontologoService(IOdontologoRepository IOdontologoRepository) {
        this.odontologoRepository = IOdontologoRepository;
    }

    @Override
    public Odontologo guardarOdontologo(Odontologo odontologo) {
        return odontologoRepository.save(odontologo);
    }

    @Override
    public List<Odontologo> obtenerTodos() {
        return odontologoRepository.findAll();
    }

    @Override
    public Optional<Odontologo> encontrarPorId(Integer id) {
        return odontologoRepository.findById(id);
    }

    @Override
    public void actualizarodontolgo(Odontologo odontologo) {
        odontologoRepository.save(odontologo);
    }

    @Override
    public void eliminarodontolgo(Integer id) {
        odontologoRepository.deleteById(id);
    }

    @Override
    public List<Odontologo> buscarPorApellidoyNombre(String apellido, String nombre) {
        return odontologoRepository.findByApellidoAndNombre(apellido, nombre);
    }

    @Override
    public List<Odontologo> buscarPorNombreSimilar(String nombre) {
        return odontologoRepository.findByNombre(nombre);
    }

    @Override
    public Optional<Odontologo> buscarPorMatricula(String matricula) {
        return odontologoRepository.findByMatricula(matricula);
    }
}
