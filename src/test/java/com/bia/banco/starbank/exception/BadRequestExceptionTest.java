package com.bia.banco.starbank.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BadRequestExceptionTest {


    @Test
    void testBadRequestException_ConstrutorComMensagem_RetornaMensagemCorreta() {

        String mensagemEsperada = "Coordenadas inválidas.";


        BadRequestException exception = new BadRequestException(mensagemEsperada);


        assertNotNull(exception, "A exceção não deve ser nula.");
        assertEquals(mensagemEsperada, exception.getMessage(), "A mensagem da exceção deve ser igual à mensagem fornecida.");
    }

    @Test
    void testBadRequestException_EhInstanciaDeRuntimeException() {

        String mensagem = "Erro de requisição inválida.";


        BadRequestException exception = new BadRequestException(mensagem);


        assertTrue(exception instanceof RuntimeException, "BadRequestException deve ser uma subclasse de RuntimeException.");
    }


    @Test
    void testBadRequestException_PodeSerLancadaECapturada() {

        Exception exception = assertThrows(BadRequestException.class, () -> {
            throw new BadRequestException("Este é um teste de lançamento de exceção.");
        });

        assertEquals("Este é um teste de lançamento de exceção.", exception.getMessage());
    }
}