package com.clinica.dh.service.impl;

import com.clinica.dh.entity.Paciente;
import com.clinica.dh.repository.IPacienteRepository;
import com.clinica.dh.service.IPacienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PacienteService implements IPacienteService {

    private final IPacienteRepository pacienteRepository;
    private static final Logger logger = LoggerFactory.getLogger(PacienteService.class);

    public PacienteService(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public Paciente guardarPaciente(Paciente paciente) { // Nombre del método debe coincidir
        logger.info("Guardando un nuevo paciente: {}", paciente.getNombre());
        try {
            Paciente pacienteGuardado = pacienteRepository.save(paciente);
            logger.info("Paciente guardado exitosamente con ID: {}", pacienteGuardado.getId());
            return pacienteGuardado;
        } catch (Exception e) {
            logger.error("Error al guardar el paciente: {}", e.getMessage());
            throw new RuntimeException("No se pudo guardar el paciente", e);
        }
    }

    @Override
    public Optional<Paciente> encontrarPorId(Integer id) {
        logger.info("Buscando paciente con ID: {}", id);
        Optional<Paciente> paciente = pacienteRepository.findById(id);
        if (paciente.isPresent()) {
            logger.info("Paciente encontrado: {}", paciente.get().getNombre());
        } else {
            logger.warn("Paciente con ID: {} no encontrado", id);
        }
        return paciente;
    }

    @Override
    public List<Paciente> buscarTodos() {
        logger.info("Buscando todos los pacientes");
        return pacienteRepository.findAll();
    }

    @Override
    public void actualizarPaciente(Paciente paciente) {
        logger.info("Actualizando paciente con ID: {}", paciente.getId());
        if (pacienteRepository.existsById(paciente.getId())) {
            try {
                pacienteRepository.save(paciente);
                logger.info("Paciente actualizado correctamente: {}", paciente.getNombre());
            } catch (Exception e) {
                logger.error("Error al actualizar el paciente: {}", e.getMessage());
                throw new RuntimeException("No se pudo actualizar el paciente", e);
            }
        } else {
            logger.warn("Paciente con ID: {} no existe y no se pudo actualizar", paciente.getId());
            throw new RuntimeException("Paciente no encontrado");
        }
    }

    @Override
    public void eliminarPaciente(Integer id) {
        logger.info("Eliminando paciente con ID: {}", id);
        if (pacienteRepository.existsById(id)) {
            try {
                pacienteRepository.deleteById(id);
                logger.info("Paciente con ID: {} eliminado correctamente", id);
            } catch (Exception e) {
                logger.error("Error al eliminar el paciente: {}", e.getMessage());
                throw new RuntimeException("No se pudo eliminar el paciente", e);
            }
        } else {
            logger.warn("Paciente con ID: {} no encontrado para eliminar", id);
            throw new RuntimeException("Paciente no encontrado");
        }
    }

    @Override
    public List<Paciente> buscarPorApellidoyNombre(String apellido, String nombre) {
        logger.info("Buscando pacientes por apellido: {} y nombre: {}", apellido, nombre);
        return pacienteRepository.findByApellidoAndNombre(apellido, nombre);
    }

    @Override
    public List<Paciente> buscarPorNombre(String nombre) {
        logger.info("Buscando pacientes por nombre: {}", nombre);
        return pacienteRepository.findByNombre(nombre);
    }
}
