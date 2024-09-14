package com.clinica.dh;

import com.clinica.dh.entity.Domicilio;
import com.clinica.dh.entity.Paciente;
import com.clinica.dh.service.IPacienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
class ServicePacienteTest {

    @Autowired
    IPacienteService servicePaciente;

    Paciente paciente;
    Paciente pacienteDB;

    @BeforeEach
    void crearPaciente() {
        Domicilio domicilio = new Domicilio(null, "Bonita", 679, "Wagne", "Medellin");
        paciente = new Paciente();
        paciente.setApellido("Tirado");
        paciente.setNombre("Laura");
        paciente.setDocumentoIdentidad("9787984"); // Cambiado a setDocumentoIdentidad
        paciente.setFechaRegistro(LocalDate.of(2024, 6, 15)); // Cambiado a setFechaRegistro
        paciente.setDomicilio(domicilio);
        pacienteDB = servicePaciente.guardarPaciente(paciente);
    }

    @Test
    @DisplayName("Testear que un paciente se guarde en la base de datos con su domicilio")
    void caso1() {
        assertNotNull(pacienteDB.getId());
    }

    @Test
    @DisplayName("Testear que un paciente pueda ser obtenido cuando se envía el id")
    void caso2() {
        Integer id = pacienteDB.getId();
        Paciente pacienteEncontrado = servicePaciente.encontrarPorId(id).orElse(null);
        assertNotNull(pacienteEncontrado);
        assertEquals(id, pacienteEncontrado.getId());
    }
}


