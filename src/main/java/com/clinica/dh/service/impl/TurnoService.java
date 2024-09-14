package com.clinica.dh.service.impl;

import com.clinica.dh.dto.request.TurnoModificarDto;
import com.clinica.dh.dto.request.TurnoRequestDto;
import com.clinica.dh.dto.response.OdontologoResponseDto;
import com.clinica.dh.dto.response.PacienteResponseDto;
import com.clinica.dh.dto.response.TurnoResponseDto;
import com.clinica.dh.entity.Odontologo;
import com.clinica.dh.entity.Paciente;
import com.clinica.dh.entity.Turno;
import com.clinica.dh.exception.ResourceNotFoundException;
import com.clinica.dh.repository.ITurnoRepository;
import com.clinica.dh.service.ITurnoService;
import org.modelmapper.ModelMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService implements ITurnoService {

    private static final Logger logger = LogManager.getLogger(TurnoService.class);

    private ITurnoRepository turnoRepository;
    private PacienteService pacienteService;
    private OdontologoService odontologoService;

    @Autowired
    private ModelMapper modelMapper;

    public TurnoService(ITurnoRepository turnoRepository, PacienteService pacienteService, OdontologoService odontologoService) {
        this.turnoRepository = turnoRepository;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }

    @Override
    public TurnoResponseDto guardarTurno(TurnoRequestDto turnoRequestDto) {
        logger.info("Iniciando proceso para guardar un turno.");

        Optional<Paciente> paciente = pacienteService.encontrarPorId(turnoRequestDto.getPaciente_id());
        Optional<Odontologo> odontologo = odontologoService.encontrarPorId(turnoRequestDto.getOdontologo_id());

        if (!paciente.isPresent()) {
            logger.error("Paciente no encontrado con el ID: {}", turnoRequestDto.getPaciente_id());
            throw new ResourceNotFoundException("Paciente no encontrado con el ID: " + turnoRequestDto.getPaciente_id());
        }
        if (!odontologo.isPresent()) {
            logger.error("Odontólogo no encontrado con el ID: {}", turnoRequestDto.getOdontologo_id());
            throw new ResourceNotFoundException("Odontólogo no encontrado con el ID: " + turnoRequestDto.getOdontologo_id());
        }

        Turno turno = new Turno();
        turno.setPaciente(paciente.get());
        turno.setOdontologo(odontologo.get());
        turno.setFechaTurno(LocalDate.parse(turnoRequestDto.getFechaTurno()));

        Turno turnoGuardado = turnoRepository.save(turno);
        logger.info("Turno guardado exitosamente con ID: {}", turnoGuardado.getId());

        return mapearATurnoResponse(turnoGuardado);
    }

    @Override
    public Optional<TurnoResponseDto> buscarPorId(Integer id) {
        logger.info("Buscando turno con ID: {}", id);

        Optional<Turno> turnoDesdeDb = turnoRepository.findById(id);
        if (!turnoDesdeDb.isPresent()) {
            logger.error("Turno no encontrado con ID: {}", id);
            throw new ResourceNotFoundException("Turno no encontrado con el ID: " + id);
        }

        return Optional.of(mapearATurnoResponse(turnoDesdeDb.get()));
    }

    @Override
    public List<TurnoResponseDto> buscarTodos() {
        logger.info("Buscando todos los turnos.");
        List<Turno> turnos = turnoRepository.findAll();
        List<TurnoResponseDto> turnosRespuesta = new ArrayList<>();
        for (Turno t : turnos) {
            turnosRespuesta.add(mapearATurnoResponse(t));
        }
        return turnosRespuesta;
    }

    @Override
    public void modificarTurno(TurnoModificarDto turnoModificarDto) {
        logger.info("Modificando turno con ID: {}", turnoModificarDto.getId());

        Optional<Paciente> paciente = pacienteService.encontrarPorId(turnoModificarDto.getPaciente_id());
        Optional<Odontologo> odontologo = odontologoService.encontrarPorId(turnoModificarDto.getOdontologo_id());

        if (!paciente.isPresent()) {
            logger.error("Paciente no encontrado con el ID: {}", turnoModificarDto.getPaciente_id());
            throw new ResourceNotFoundException("Paciente no encontrado con el ID: " + turnoModificarDto.getPaciente_id());
        }
        if (!odontologo.isPresent()) {
            logger.error("Odontólogo no encontrado con el ID: {}", turnoModificarDto.getOdontologo_id());
            throw new ResourceNotFoundException("Odontólogo no encontrado con el ID: " + turnoModificarDto.getOdontologo_id());
        }

        Turno turno = new Turno(turnoModificarDto.getId(), paciente.get(), odontologo.get(),
                LocalDate.parse(turnoModificarDto.getFechaTurno()));

        turnoRepository.save(turno);
        logger.info("Turno modificado exitosamente con ID: {}", turno.getId());
    }

    @Override
    public void eliminarTurno(Integer id) {
        logger.info("Eliminando turno con ID: {}", id);

        Optional<Turno> turnoDesdeDb = turnoRepository.findById(id);
        if (!turnoDesdeDb.isPresent()) {
            logger.error("Turno no encontrado con ID: {}", id);
            throw new ResourceNotFoundException("Turno no encontrado con el ID: " + id);
        }

        turnoRepository.deleteById(id);
        logger.info("Turno eliminado exitosamente con ID: {}", id);
    }

    public TurnoResponseDto mapearATurnoResponse(Turno turno) {
        TurnoResponseDto turnoResponseDto = modelMapper.map(turno, TurnoResponseDto.class);
        turnoResponseDto.setOdontologoResponseDto(modelMapper.map(turno.getOdontologo(), OdontologoResponseDto.class));
        turnoResponseDto.setPacienteResponseDto(modelMapper.map(turno.getPaciente(), PacienteResponseDto.class));
        turnoResponseDto.setFechaTurno(turno.getFechaTurno().toString());
        return turnoResponseDto;
    }

    @Override
    public List<Turno> buscarTurnoPaciente(String apellidoPaciente) {
        logger.info("Buscando turnos por apellido del paciente: {}", apellidoPaciente);
        return turnoRepository.buscarTurnoPorApellidoPaciente(apellidoPaciente);
    }

    @Override
    public List<Turno> buscarTurnoOdontologo(String matricula) {
        logger.info("Buscando turnos por matrícula del odontólogo: {}", matricula);
        return turnoRepository.buscarTurnoPorMatriculaOdontologo(matricula);
    }
}
