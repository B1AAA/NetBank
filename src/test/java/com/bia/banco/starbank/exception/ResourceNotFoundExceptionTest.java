package com.bia.banco.starbank.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceNotFoundExceptionTest {


    @Test
    void testResourceNotFoundException_ConstrutorComMensagem_RetornaMensagemCorreta() {

        String mensagemEsperada = "Agência com ID 999 não encontrada.";


        ResourceNotFoundException exception = new ResourceNotFoundException(mensagemEsperada);


        assertNotNull(exception, "A exceção não deve ser nula.");
        assertEquals(mensagemEsperada, exception.getMessage(), "A mensagem da exceção deve ser igual à mensagem fornecida.");
    }

    @Test
    void testResourceNotFoundException_EhInstanciaDeRuntimeException() {

        String mensagem = "Recurso não encontrado.";


        ResourceNotFoundException exception = new ResourceNotFoundException(mensagem);


        assertTrue(exception instanceof RuntimeException, "ResourceNotFoundException deve ser uma subclasse de RuntimeException.");
    }


    @Test
    void testResourceNotFoundException_PodeSerLancadaECapturada() {

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            throw new ResourceNotFoundException("Este é um teste de lançamento de exceção.");
        });

        assertEquals("Este é um teste de lançamento de exceção.", exception.getMessage());
    }
}