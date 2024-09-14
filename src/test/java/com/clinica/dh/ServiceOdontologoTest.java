package com.clinica.dh;

import com.clinica.dh.service.impl.OdontologoService;
import com.clinica.dh.entity.Odontologo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
class ServiceOdontologoTest {

    @Autowired
    private OdontologoService serviceOdontologo;

    private Odontologo odontologo;
    private Odontologo odontologoDB;

    @BeforeEach
    void setUp() {
        odontologo = new Odontologo();
        odontologo.setMatricula("1234567");
        odontologo.setApellido("Romero");
        odontologo.setNombre("Luciana");
        odontologoDB = serviceOdontologo.registrarOdontologo(odontologo); // Asegúrate de que este nombre sea el correcto
    }

    @Test
    @DisplayName("Testear que un odontologo se guarde en la base de datos")
    void caso1() {
        assertNotNull(odontologoDB.getId(), "El ID del odontologo no debería ser nulo");
    }

    @Test
    @DisplayName("Testear que un odontologo pueda ser obtenido cuando se envía el id")
    void caso2() {
        Integer id = odontologoDB.getId();
        Odontologo odontologoEncontrado = serviceOdontologo.encontrarPorId(id).orElse(null); // Usa encontrarPorId en lugar de buscarId
        assertNotNull(odontologoEncontrado, "El odontologo debería ser encontrado");
        assertEquals(id, odontologoEncontrado.getId(), "El ID del odontologo no coincide");
    }
}
