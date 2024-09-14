package com.clinica.dh.service.impl;

import com.clinica.dh.entity.Odontologo;
import com.clinica.dh.repository.IOdontologoRepository;
import com.clinica.dh.service.IOdontologoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OdontologoService implements IOdontologoService {

    private final IOdontologoRepository odontologoRepository;
    private static final Logger logger = LoggerFactory.getLogger(OdontologoService.class);

    public OdontologoService(IOdontologoRepository odontologoRepository) {
        this.odontologoRepository = odontologoRepository;
    }

    @Override
    public Odontologo registrarOdontologo(Odontologo odontologo) {
        logger.info("Registrando un nuevo odontólogo: {}", odontologo.getNombre());
        try {
            Odontologo odontologoGuardado = odontologoRepository.save(odontologo);
            logger.info("Odontólogo registrado exitosamente con ID: {}", odontologoGuardado.getId());
            return odontologoGuardado;
        } catch (Exception e) {
            logger.error("Error al registrar el odontólogo: {}", e.getMessage());
            throw new RuntimeException("No se pudo registrar el odontólogo", e);
        }
    }

    @Override
    public List<Odontologo> obtenerTodos() {
        logger.info("Buscando todos los odontólogos");
        return odontologoRepository.findAll();
    }

    @Override
    public Optional<Odontologo> encontrarPorId(Integer id) {
        logger.info("Buscando odontólogo con ID: {}", id);
        Optional<Odontologo> odontologo = odontologoRepository.findById(id);
        if (odontologo.isPresent()) {
            logger.info("Odontólogo encontrado: {}", odontologo.get().getNombre());
        } else {
            logger.warn("Odontólogo con ID: {} no encontrado", id);
        }
        return odontologo;
    }

    @Override
    public void actualizarOdontologo(Odontologo odontologo) {
        logger.info("Actualizando odontólogo con ID: {}", odontologo.getId());
        if (odontologoRepository.existsById(odontologo.getId())) {
            try {
                odontologoRepository.save(odontologo);
                logger.info("Odontólogo actualizado correctamente: {}", odontologo.getNombre());
            } catch (Exception e) {
                logger.error("Error al actualizar el odontólogo: {}", e.getMessage());
                throw new RuntimeException("No se pudo actualizar el odontólogo", e);
            }
        } else {
            logger.warn("Odontólogo con ID: {} no existe y no se pudo actualizar", odontologo.getId());
            throw new RuntimeException("Odontólogo no encontrado");
        }
    }

    @Override
    public void eliminarOdontologo(Integer id) {
        logger.info("Eliminando odontólogo con ID: {}", id);
        if (odontologoRepository.existsById(id)) {
            try {
                odontologoRepository.deleteById(id);
                logger.info("Odontólogo con ID: {} eliminado correctamente", id);
            } catch (Exception e) {
                logger.error("Error al eliminar el odontólogo: {}", e.getMessage());
                throw new RuntimeException("No se pudo eliminar el odontólogo", e);
            }
        } else {
            logger.warn("Odontólogo con ID: {} no encontrado para eliminar", id);
            throw new RuntimeException("Odontólogo no encontrado");
        }
    }

    @Override
    public List<Odontologo> buscarPorApellidoyNombre(String apellido, String nombre) {
        logger.info("Buscando odontólogos por apellido: {} y nombre: {}", apellido, nombre);
        return odontologoRepository.findByApellidoAndNombre(apellido, nombre);
    }

    @Override
    public List<Odontologo> buscarPorNombreSimilar(String nombre) {
        logger.info("Buscando odontólogos por nombre similar: {}", nombre);
        return odontologoRepository.findByNombreLike(nombre);
    }

    @Override
    public Optional<Odontologo> buscarPorMatricula(String matricula) {
        logger.info("Buscando odontólogo por matrícula: {}", matricula);
        return odontologoRepository.findByMatricula(matricula);
    }
}

