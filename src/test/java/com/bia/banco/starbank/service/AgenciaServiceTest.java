package com.bia.banco.starbank.service;


import com.bia.banco.starbank.dto.AgenciaCadastroDto;
import com.bia.banco.starbank.dto.response.AgenciaResponse;
import com.bia.banco.starbank.exception.ResourceNotFoundException;
import com.bia.banco.starbank.model.Agencia;
import com.bia.banco.starbank.repository.AgenciaRepository;
import com.bia.banco.starbank.service.impl.AgenciaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

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
        ReflectionTestUtils.setField(agenciaService, "agenciaRepository", agenciaRepository);
    }

    @Test
    void testCadastrarAgencia_ComDadosValidos_RetornaAgenciaResponse() {

        AgenciaCadastroDto dto = new AgenciaCadastroDto();
        dto.setPositionX(10.0);
        dto.setPositionY(-5.0);

        Agencia agenciaSalva = new Agencia();
        agenciaSalva.setId(1L);
        agenciaSalva.setPositionX(10.0);
        agenciaSalva.setPositionY(-5.0);
        when(agenciaRepository.save(any(Agencia.class))).thenReturn(agenciaSalva);


        AgenciaResponse response = agenciaService.cadastrarAgencia(dto);


        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(10.0, response.getPositionX());
        assertEquals(-5.0, response.getPositionY());
        verify(agenciaRepository, times(1)).save(any(Agencia.class));
    }

    @Test
    void testCalcularDistancias_AtualizaResultadoERetornaOrdenado() {

        Agencia ag1 = new Agencia();
        ag1.setId(1L);
        ag1.setPositionX(0.0);
        ag1.setPositionY(0.0);

        Agencia ag2 = new Agencia();
        ag2.setId(2L);
        ag2.setPositionX(3.0);
        ag2.setPositionY(4.0);

        when(agenciaRepository.findAll()).thenReturn(Arrays.asList(ag1, ag2));


        Map<String, String> resultado = agenciaService.calcularDistancias(3.0, 4.0);


        assertEquals(2, resultado.size());


        assertTrue(resultado.containsKey("AGENCIA_2"));
        assertTrue(resultado.containsKey("AGENCIA_1"));


        String primeiraChave = resultado.keySet().iterator().next();
        assertEquals("AGENCIA_2", primeiraChave);


        assertEquals(5.0, ag1.getResultado(), 0.01);
        assertEquals(0.0, ag2.getResultado(), 0.01);


        verify(agenciaRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testDeletarAgencia_AgenciaExistente_DeletaComSucesso() {
        Long id = 1L;
        when(agenciaRepository.existsById(id)).thenReturn(true);

        agenciaService.deletarAgencia(id);

        verify(agenciaRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeletarAgencia_AgenciaInexistente_LancaExcecao() {
        Long id = 999L;
        when(agenciaRepository.existsById(id)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            agenciaService.deletarAgencia(id);
        });

        assertEquals("Agência com ID 999 não encontrada.", exception.getMessage());
        verify(agenciaRepository, never()).deleteById(id);
    }

    @Test
    void testDeletarAgencia_AgenciaInexistente_LancaResourceNotFoundException() {
        Long idInexistente = 999L;
        when(agenciaRepository.existsById(idInexistente)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            agenciaService.deletarAgencia(idInexistente);
        });

        assertEquals("Agência com ID 999 não encontrada.", exception.getMessage());
        verify(agenciaRepository, never()).deleteById(idInexistente);
    }
}