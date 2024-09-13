package com.clinica.dh.service.impl;

import com.clinica.dh.dto.request.TurnoModificarDto;
import com.clinica.dh.dto.request.TurnoRequestDto;
import com.clinica.dh.dto.response.OdontologoResponseDto;
import com.clinica.dh.dto.response.PacienteResponseDto;
import com.clinica.dh.dto.response.TurnoResponseDto;
import com.clinica.dh.entity.Odontologo;
import com.clinica.dh.entity.Paciente;
import com.clinica.dh.entity.Turno;
import com.clinica.dh.repository.ITurnoRepository;
import com.clinica.dh.service.ITurnoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService implements ITurnoService {
    private ITurnoRepository turnoRepository;
    private PacienteService pacienteService;
    private OdontologoService odontologService;
    @Autowired
    private ModelMapper modelMapper;

    public TurnoService(ITurnoRepository turnoRepository, PacienteService pacienteService, OdontologoService odontologService) {
        this.turnoRepository = turnoRepository;
        this.pacienteService = pacienteService;
        this.odontologService = odontologService;
    }

    @Override
    public TurnoResponseDto guardarTurno(TurnoRequestDto turnoRequestDto){
        Optional<Paciente> paciente = pacienteService.encontrarPorId(turnoRequestDto.getPaciente_id());
        Optional<Odontologo> odontologo = odontologService.encontrarPorId(turnoRequestDto.getOdontologo_id());
        Turno turno = new Turno();
        Turno turnoDesdeDb = null;
        TurnoResponseDto turnoARetornar = null;
        if (paciente.isPresent() && odontologo.isPresent()) {
            // mapear el turnoRequestDto a turno
            turno.setPaciente(paciente.get());
            turno.setOdontologo(odontologo.get());
            turno.setFecha(LocalDate.parse(turnoRequestDto.getFecha()));
            // voy a persistir el turno
            turnoDesdeDb = turnoRepository.save(turno);

            // mapear el turnoDesdeDb a turnoResponseDto
            // turno mapeado a mano
            //turnoARetornar = convertirTurnoAResponse(turnoDesdeDb);
            // turno mapeado con modelmapper
            turnoARetornar = mapearATurnoResponse(turnoDesdeDb);
        }
        return turnoARetornar;
    }

    @Override
    public Optional<TurnoResponseDto> buscarPorId(Integer id) {
        Optional<Turno> turnoDesdeDb = turnoRepository.findById(id);
        TurnoResponseDto turnoResponseDto = null;
        if(turnoDesdeDb.isPresent()){
            turnoResponseDto = mapearATurnoResponse(turnoDesdeDb.get());
        }
        return Optional.ofNullable(turnoResponseDto);
    }

    @Override
    public List<TurnoResponseDto> buscarTodos() {
        List<Turno> turnos = turnoRepository.findAll();
        List<TurnoResponseDto> turnosRespuesta = new ArrayList<>();
        for (Turno t: turnos){
            TurnoResponseDto turnoAuxiliar = mapearATurnoResponse(t);
            turnosRespuesta.add(turnoAuxiliar);
        }
        return turnosRespuesta;
    }

    @Override
    public void modificarTurno(TurnoModificarDto turnoModificarDto) {
        Optional<Paciente> paciente = pacienteService.encontrarPorId(turnoModificarDto.getPaciente_id());
        Optional<Odontologo> odontologo = odontologService.encontrarPorId(turnoModificarDto.getOdontologo_id());
        Turno turno = null;
        if (paciente.isPresent() && odontologo.isPresent()) {
            turno = new Turno(turnoModificarDto.findById(), paciente.get(), odontologo.get(),
                    LocalDate.parse(turnoModificarDto.getFecha()) );
            // voy a persistir el turno
            turnoRepository.save(turno);
        }
    }

    @Override
    public void eliminarTurno(Integer id) {
        turnoRepository.deleteById(id);
    }

    private TurnoResponseDto convertirTurnoAResponse(Turno turnoDesdeDb){
        OdontologoResponseDto odontologoResponseDto = new OdontologoResponseDto(
                turnoDesdeDb.getOdontologo().getId(), turnoDesdeDb.getOdontologo().getMatricula(),
                turnoDesdeDb.getOdontologo().getNombre(), turnoDesdeDb.getOdontologo().getApellido()
        );

        PacienteResponseDto pacienteResponseDto = new PacienteResponseDto(
                turnoDesdeDb.getPaciente().getId(), turnoDesdeDb.getPaciente().getNombre(),
                turnoDesdeDb.getPaciente().getApellido(), turnoDesdeDb.getPaciente().getDni()
        );

        TurnoResponseDto turnoARetornar = new TurnoResponseDto(
                turnoDesdeDb.getId(), pacienteResponseDto, odontologoResponseDto,
                turnoDesdeDb.getFecha().toString()
        );
        return turnoARetornar;
    }

    private TurnoResponseDto mapearATurnoResponse(Turno turno){
        TurnoResponseDto turnoResponseDto = modelMapper.map(turno, TurnoResponseDto.class);
        turnoResponseDto.setOdontologoResponseDto(modelMapper.map(turno.getOdontologo(), OdontologoResponseDto.class));
        turnoResponseDto.setPacienteResponseDto(modelMapper.map(turno.getPaciente(), PacienteResponseDto.class));
        return turnoResponseDto;
    }

    @Override
    public List<Turno> buscarTurnoPaciente(String apellidoPaciente){
        return turnoRepository.buscarTurnoPorApellidoPaciente(apellidoPaciente);
    }

    @Override
    public List<Turno> buscarTurnoOdontologo(String matriculaOdontologo){
        return turnoRepository.buscarTurnoPorMatriculaOdontologo(matriculaOdontologo);
    }

}
