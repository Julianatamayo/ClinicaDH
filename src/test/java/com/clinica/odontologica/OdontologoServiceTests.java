package com.clinica.odontologica;

import com.clinica.odontologica.service.impl.OdontologoService;
import com.clinica.odontologica.entity.Odontologo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
class OdontologoServiceTest {
    @Autowired
    OdontologoService OdontologoService;

    Odontologo odontologo;
    Odontologo odontologoDB;

    @BeforeEach
    void crearOdontologo(){
        odontologo = new Odontologo();
        odontologo.setMatricula("1234567");
        odontologo.setApellido("Romero");
        odontologo.setNombre("Luciana");
        odontologoDB = OdontologoService.guardarOdontologo(odontologo);
    }

    @Test
    @DisplayName("Testear que un odontologo se guarde en la base de datos")
    void caso1(){
        //dado
        // cuando
        // entonces
        assertNotNull(odontologoDB.getId());
    }

    @Test
    @DisplayName("Testear que un odontologo pueda ser obtenido cuando se envia el id")
    void caso2(){
        //dado
        Integer id = odontologoDB.getId();
        // cuando
        Odontologo odontologoEncontrado = OdontologoService.buscarId(id).get();
        // entonces
        assertEquals(id, odontologoEncontrado.getId());
    }
}