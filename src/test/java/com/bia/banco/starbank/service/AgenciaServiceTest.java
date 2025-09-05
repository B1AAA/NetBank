package com.bia.banco.starbank.service;


import com.bia.banco.starbank.dto.AgenciaCadastroDto;
import com.bia.banco.starbank.dto.response.AgenciaResponse;
import com.bia.banco.starbank.model.Agencia;
import com.bia.banco.starbank.repository.AgenciaRepository;
import com.bia.banco.starbank.service.impl.AgenciaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AgenciaServiceTest {

    @Mock
    private AgenciaRepository agenciaRepository;

    @InjectMocks
    private AgenciaServiceImpl agenciaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testCadastrarAgencia() {
        AgenciaCadastroDto dto = new AgenciaCadastroDto();
        dto.setPosX(10.0);
        dto.setPosY(-5.0);


        Agencia agenciaSalva = new Agencia();
        agenciaSalva.setId(1L);
        agenciaSalva.setPosX(10.0);
        agenciaSalva.setPosY(-5.0);
        when(agenciaRepository.save(any(Agencia.class))).thenReturn(agenciaSalva);


        AgenciaResponse response = agenciaService.cadastrarAgencia(dto);


        assertNotNull(response, "A resposta não deve ser nula");
        assertEquals(1L, response.getId(), "O ID da agência retornada deve ser 1");
        assertEquals(10.0, response.getPosX(), "A coordenada X deve ser 10.0");
        assertEquals(-5.0, response.getPosY(), "A coordenada Y deve ser -5.0");


        verify(agenciaRepository, times(1)).save(any(Agencia.class));
    }


    @Test
    void testCalcularDistancias() {

        Agencia ag1 = new Agencia();
        ag1.setPosX(0.0);
        ag1.setPosY(0.0);
        ag1.setId(1L);

        Agencia ag2 = new Agencia();
        ag2.setPosX(3.0);
        ag2.setPosY(4.0);
        ag2.setId(2L);

        when(agenciaRepository.findAll()).thenReturn(Arrays.asList(ag1, ag2));


        Map<String, String> resultado = agenciaService.calcularDistancias(3.0, 4.0);


        assertEquals(2, resultado.size(), "O mapa deve conter 2 entradas");


        assertTrue(resultado.containsKey("AGENCIA_2"), "Deve conter a chave 'AGENCIA_2'");
        assertTrue(resultado.containsKey("AGENCIA_1"), "Deve conter a chave 'AGENCIA_1'");


        String primeiraChave = resultado.keySet().iterator().next();
        assertEquals("AGENCIA_2", primeiraChave, "A primeira agência deve ser a mais próxima (ID 2)");
    }
}