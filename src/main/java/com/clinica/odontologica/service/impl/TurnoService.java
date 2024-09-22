package com.clinica.odontologica.service.impl;

import com.clinica.odontologica.dto.request.*;
import com.clinica.odontologica.dto.response.*;
import com.clinica.odontologica.entity.Odontologo;
import com.clinica.odontologica.entity.Paciente;
import com.clinica.odontologica.entity.Turno;
import com.clinica.odontologica.exception.ResourceNotFoundException;
import com.clinica.odontologica.repository.ITurnoRepository;
import com.clinica.odontologica.service.ITurnoService;
import com.clinica.odontologica.service.IOdontologoService;
import com.clinica.odontologica.service.IPacienteService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService implements ITurnoService {
    private final Logger logger = LoggerFactory.getLogger(TurnoService.class);
    private ITurnoRepository turnoRepository;
    private IPacienteService pacienteService;
    private IOdontologoService odontologoService;
    @Autowired
    private ModelMapper modelMapper;

    public TurnoService(ITurnoRepository turnoRepository, PacienteService pacienteService, OdontologoService odontologoService){
        this.turnoRepository = turnoRepository;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }

    @Override
    public TurnoResponseDto guardarTurno(TurnoRequestDto turnoRequestDto){
        Optional<Paciente> paciente = pacienteService.buscarId(turnoRequestDto.getPaciente_id());
        Optional<Odontologo> odontologo = odontologoService.buscarId(turnoRequestDto.getOdontologo_id());
        Turno turno = new Turno();
        Turno turnoDesdeDb = null;
        TurnoResponseDto turnoARetornar = null;
        if (paciente.isPresent() && odontologo.isPresent()) {

            turno.setPaciente(paciente.get());
            turno.setOdontologo(odontologo.get());
            turno.setFecha(LocalDate.parse(turnoRequestDto.getFecha()));
            turnoDesdeDb = turnoRepository.save(turno);
            turnoARetornar = turnoMappingResponse(turnoDesdeDb);
            logger.info("Turno guardado correctamente.");
            return turnoARetornar;
        } else {
            logger.info("El turno no pudo ser guardado. El Odontólogo o el paciente no existen.");
            throw new ResourceNotFoundException("El turno no pudo ser guardado, el Odontólogo o el paciente no existen.");
        }
    }

    @Override
    public Optional<TurnoResponseDto> buscarId(Integer id){
        Optional<Turno> turnoDB = turnoRepository.findById(id);
        TurnoResponseDto turnoResponseDto = turnoMappingResponse(turnoDB.get());
        logger.info("Turno encontrado: " + turnoResponseDto);
        return Optional.of(turnoResponseDto);
    }

    @Override
    public List<TurnoResponseDto> buscarTodos(){
        List<Turno> turnos = turnoRepository.findAll();
        List<TurnoResponseDto> turnoReturn = new ArrayList<>();
        for (Turno t: turnos){
            TurnoResponseDto turnoAux = turnoMappingResponse(t);
            turnoReturn.add(turnoAux);
        }
        return turnoReturn;
    };

    @Override
    public void modificarTurno(TurnoModifyDto turnoModifyDto) {
        Optional<Paciente> paciente = pacienteService.buscarId(turnoModifyDto.getPaciente_id());
        Optional<Odontologo> odontologo = odontologoService.buscarId(turnoModifyDto.getOdontologo_id());
        Turno turno;
        if (paciente.isPresent() && odontologo.isPresent()) {
            turno = new Turno(turnoModifyDto.getId(), paciente.get(), odontologo.get(), LocalDate.parse(turnoModifyDto.getFecha()));
            turnoRepository.save(turno);
        }
    }

    @Override
    public void eliminarTurno(Integer id) {
        Optional<Turno> turnoEncontrado = turnoRepository.findById(id);
        if(turnoEncontrado.isPresent()){
            logger.info("Paciente eliminado satisfactoriamente.");
            turnoRepository.deleteById(id);
        }else{
            logger.info("El paciente no fue encontrado. Id: " + id + " Not found.");
            throw new ResourceNotFoundException("El paciente no fue encontrado. Id: " + id + " Not found.");
        }
    }

    private TurnoResponseDto obtainTurnoResponse(Turno turnoDB){
        OdontologoResponseDto odontologoResponseDto = new OdontologoResponseDto(
                turnoDB.getOdontologo().getId(), turnoDB.getOdontologo().getMatricula(),
                turnoDB.getOdontologo().getNombre(), turnoDB.getOdontologo().getApellido()
        );

        PacienteResponseDto pacienteResponseDto = new PacienteResponseDto(
                turnoDB.getPaciente().getId(), turnoDB.getPaciente().getNombre(),
                turnoDB.getPaciente().getApellido(), turnoDB.getPaciente().getDni()
        );

        TurnoResponseDto turnoReturn = new TurnoResponseDto(
                turnoDB.getId(), pacienteResponseDto, odontologoResponseDto,
                turnoDB.getFecha().toString()
        );
        return turnoReturn;
    };

    private TurnoResponseDto turnoMappingResponse(Turno turno){
        TurnoResponseDto turnoResponseDto = modelMapper.map(turno, TurnoResponseDto.class);
        turnoResponseDto.setOdontologoResponseDto(modelMapper.map(turno.getOdontologo(), OdontologoResponseDto.class));
        turnoResponseDto.setPacienteResponseDto(modelMapper.map(turno.getPaciente(), PacienteResponseDto.class));
        return turnoResponseDto;
    }


    @Override
    public List<Turno> buscarTurnoPaciente(String apellidoPaciente){
        return turnoRepository.buscarTurnoApellidoPaciente(apellidoPaciente);
    }


    @Override
    public List<Turno> buscarTurnoOdontologo(String matriculaOdontologo){
        List<Turno> turnosEncontrados = turnoRepository.buscarTurnoMatriculaOdontologo(matriculaOdontologo);
        if (turnosEncontrados.isEmpty()) {
            logger.info("No se encontraron turnos para este odontologo");
            throw new ResourceNotFoundException("No se encontraron turnos para este odontologo");
        }else {
            logger.info("Turnos encontrados: " + turnosEncontrados.size());
            return turnosEncontrados;
        }
    }

}

